package Qlda.dto.response;

import Qlda.entity.Student;

public record StudentResponse(Long id, String studentCode, String fullName,
                              String className, String email, String phone) {
    public static StudentResponse from(Student s) {
        return new StudentResponse(s.getId(), s.getStudentCode(), s.getFullName(),
                s.getClassName(), s.getEmail(), s.getPhone());
    }
}