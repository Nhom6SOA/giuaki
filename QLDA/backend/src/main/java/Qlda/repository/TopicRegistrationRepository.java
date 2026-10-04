package Qlda.repository;

import Qlda.entity.TopicRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRegistrationRepository extends JpaRepository<TopicRegistration, Long> {
    boolean existsByStudentIdAndTopicId(Long studentId, Long topicId);
    long countByTopicIdAndStatus(Long topicId, TopicRegistration.Status status);
}