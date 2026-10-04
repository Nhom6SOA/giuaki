package Qlda.dto.request;

import jakarta.validation.constraints.NotNull;

public record TopicRegistrationRequest(
        @NotNull(message = "Vui lòng chọn sinh viên") Long studentId,
        @NotNull(message = "Vui lòng chọn đề tài") Long topicId,
        String status,
        String note) {
}