package Qlda.service;

import Qlda.dto.request.StudentRequest;
import Qlda.dto.response.StudentResponse;
import Qlda.entity.Student;
import Qlda.exception.BadRequestException;
import Qlda.exception.ResourceNotFoundException;
import Qlda.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<StudentResponse> getAll() {
        return repo.findAll().stream().map(StudentResponse::from).toList();
    }

    public StudentResponse getById(Long id) {
        return StudentResponse.from(find(id));
    }

    public StudentResponse create(StudentRequest req) {
        if (repo.existsByStudentCode(req.studentCode())) {
            throw new BadRequestException("Mã sinh viên đã tồn tại: " + req.studentCode());
        }
        return StudentResponse.from(repo.save(apply(new Student(), req)));
    }

    public StudentResponse update(Long id, StudentRequest req) {
        Student s = find(id);
        if (!s.getStudentCode().equals(req.studentCode()) && repo.existsByStudentCode(req.studentCode())) {
            throw new BadRequestException("Mã sinh viên đã tồn tại: " + req.studentCode());
        }
        return StudentResponse.from(repo.save(apply(s, req)));
    }

    public void delete(Long id) {
        repo.delete(find(id));
    }

    private Student find(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sinh viên có id = " + id));
    }

    private Student apply(Student s, StudentRequest r) {
        s.setStudentCode(r.studentCode());
        s.setFullName(r.fullName());
        s.setClassName(r.className());
        s.setEmail(r.email());
        s.setPhone(r.phone());
        return s;
    }
}