package Qlda.controller;

import Qlda.dto.request.StudentRequest;
import Qlda.dto.response.ApiResponse;
import Qlda.dto.response.StudentResponse;
import Qlda.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<StudentResponse>> getAll() {
        return ApiResponse.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentResponse> getById(@PathVariable Long id) {
        return ApiResponse.ok(service.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<StudentResponse> create(@Valid @RequestBody StudentRequest request) {
        return ApiResponse.ok("Tạo mới thành công", service.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<StudentResponse> update(@PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        return ApiResponse.ok("Cập nhật thành công", service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok("Xóa thành công", null);
    }
}