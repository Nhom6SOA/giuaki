package Qlda.dto.response;

import Qlda.entity.TopicRegistration;

import java.time.LocalDate;

public record TopicRegistrationResponse(Long id, Long studentId, String studentCode, String studentName,
                                        Long topicId, String topicTitle, LocalDate registeredAt,
                                        String status, String note) {
    public static TopicRegistrationResponse from(TopicRegistration r) {
        return new TopicRegistrationResponse(r.getId(),
                r.getStudent().getId(), r.getStudent().getStudentCode(), r.getStudent().getFullName(),
                r.getTopic().getId(), r.getTopic().getTitle(),
                r.getRegisteredAt(), r.getStatus().name(), r.getNote());
    }
}