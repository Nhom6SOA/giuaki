package Qlda.controller;

import Qlda.dto.request.TopicRegistrationRequest;
import Qlda.dto.response.ApiResponse;
import Qlda.dto.response.TopicRegistrationResponse;
import Qlda.service.TopicRegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
@CrossOrigin(origins = "*")
public class TopicRegistrationController {
    private final TopicRegistrationService service;

    public TopicRegistrationController(TopicRegistrationService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<TopicRegistrationResponse>> getAll() {
        return ApiResponse.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<TopicRegistrationResponse> getById(@PathVariable Long id) {
        return ApiResponse.ok(service.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TopicRegistrationResponse> create(@Valid @RequestBody TopicRegistrationRequest request) {
        return ApiResponse.ok("Tạo mới thành công", service.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<TopicRegistrationResponse> update(@PathVariable Long id, @Valid @RequestBody TopicRegistrationRequest request) {
        return ApiResponse.ok("Cập nhật thành công", service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok("Xóa thành công", null);
    }
}