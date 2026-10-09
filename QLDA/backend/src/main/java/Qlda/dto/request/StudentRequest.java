package Qlda.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record StudentRequest(
        @NotBlank(message = "Mã sinh viên không được để trống") String studentCode,
        @NotBlank(message = "Họ tên không được để trống") String fullName,
        String className,
        @Email(message = "Email không hợp lệ") String email,
        String phone) {
}