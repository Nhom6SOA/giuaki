package Qlda.service;

import Qlda.dto.request.TopicRequest;
import Qlda.dto.response.TopicResponse;
import Qlda.entity.Topic;
import Qlda.exception.BadRequestException;
import Qlda.exception.ResourceNotFoundException;
import Qlda.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    private final TopicRepository repo;

    public TopicService(TopicRepository repo) {
        this.repo = repo;
    }

    public List<TopicResponse> getAll() {
        return repo.findAll().stream().map(TopicResponse::from).toList();
    }

    public TopicResponse getById(Long id) {
        return TopicResponse.from(find(id));
    }

    public TopicResponse create(TopicRequest req) {
        if (repo.existsByTopicCode(req.topicCode())) {
            throw new BadRequestException("Mã đề tài đã tồn tại: " + req.topicCode());
        }
        return TopicResponse.from(repo.save(apply(new Topic(), req)));
    }

    public TopicResponse update(Long id, TopicRequest req) {
        Topic t = find(id);
        if (!t.getTopicCode().equals(req.topicCode()) && repo.existsByTopicCode(req.topicCode())) {
            throw new BadRequestException("Mã đề tài đã tồn tại: " + req.topicCode());
        }
        return TopicResponse.from(repo.save(apply(t, req)));
    }

    public void delete(Long id) {
        repo.delete(find(id));
    }

    private Topic find(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đề tài có id = " + id));
    }

    private Topic apply(Topic t, TopicRequest r) {
        t.setTopicCode(r.topicCode());
        t.setTitle(r.title());
        t.setDescription(r.description());
        t.setSupervisor(r.supervisor());
        t.setMaxStudents(r.maxStudents() == null ? 1 : r.maxStudents());
        t.setStatus(r.status() == null || r.status().isBlank() ? "OPEN" : r.status());
        return t;
    }
}