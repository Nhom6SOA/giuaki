package Qlda.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record TopicRequest(
        @NotBlank(message = "Mã đề tài không được để trống") String topicCode,
        @NotBlank(message = "Tên đề tài không được để trống") String title,
        String description,
        String supervisor,
        @Min(value = 1, message = "Số lượng sinh viên tối thiểu là 1") Integer maxStudents,
        String status) {
}