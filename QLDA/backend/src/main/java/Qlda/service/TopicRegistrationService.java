package Qlda.service;

import Qlda.dto.request.TopicRegistrationRequest;
import Qlda.dto.response.TopicRegistrationResponse;
import Qlda.entity.Student;
import Qlda.entity.Topic;
import Qlda.entity.TopicRegistration;
import Qlda.entity.TopicRegistration.Status;
import Qlda.exception.BadRequestException;
import Qlda.exception.ResourceNotFoundException;
import Qlda.repository.StudentRepository;
import Qlda.repository.TopicRegistrationRepository;
import Qlda.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TopicRegistrationService {
    private final TopicRegistrationRepository repo;
    private final StudentRepository studentRepo;
    private final TopicRepository topicRepo;

    public TopicRegistrationService(TopicRegistrationRepository repo,
                                    StudentRepository studentRepo,
                                    TopicRepository topicRepo) {
        this.repo = repo;
        this.studentRepo = studentRepo;
        this.topicRepo = topicRepo;
    }

    @Transactional(readOnly = true)
    public List<TopicRegistrationResponse> getAll() {
        return repo.findAll().stream().map(TopicRegistrationResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public TopicRegistrationResponse getById(Long id) {
        return TopicRegistrationResponse.from(find(id));
    }

    @Transactional
    public TopicRegistrationResponse create(TopicRegistrationRequest req) {
        Student student = studentRepo.findById(req.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sinh viên có id = " + req.studentId()));
        Topic topic = topicRepo.findById(req.topicId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đề tài có id = " + req.topicId()));

        if ("CLOSED".equals(topic.getStatus())) {
            throw new BadRequestException("Đề tài đã đóng đăng ký");
        }
        if (repo.existsByStudentIdAndTopicId(student.getId(), topic.getId())) {
            throw new BadRequestException("Sinh viên đã đăng ký đề tài này rồi");
        }
        if (repo.countByTopicIdAndStatus(topic.getId(), Status.APPROVED) >= topic.getMaxStudents()) {
            throw new BadRequestException("Đề tài đã đủ số lượng sinh viên");
        }

        TopicRegistration r = new TopicRegistration();
        r.setStudent(student);
        r.setTopic(topic);
        r.setRegisteredAt(LocalDate.now());
        r.setStatus(Status.PENDING);
        r.setNote(req.note());
        return TopicRegistrationResponse.from(repo.save(r));
    }

    /** Cập nhật trạng thái (duyệt / từ chối) và ghi chú. */
    @Transactional
    public TopicRegistrationResponse update(Long id, TopicRegistrationRequest req) {
        TopicRegistration r = find(id);
        if (req.status() != null && !req.status().isBlank()) {
            Status newStatus;
            try {
                newStatus = Status.valueOf(req.status());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Trạng thái không hợp lệ: " + req.status());
            }
            if (newStatus == Status.APPROVED && r.getStatus() != Status.APPROVED
                    && repo.countByTopicIdAndStatus(r.getTopic().getId(), Status.APPROVED) >= r.getTopic().getMaxStudents()) {
                throw new BadRequestException("Đề tài đã đủ số lượng sinh viên, không thể duyệt thêm");
            }
            r.setStatus(newStatus);
        }
        r.setNote(req.note());
        return TopicRegistrationResponse.from(repo.save(r));
    }

    public void delete(Long id) {
        repo.delete(find(id));
    }

    private TopicRegistration find(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đăng ký có id = " + id));
    }
}