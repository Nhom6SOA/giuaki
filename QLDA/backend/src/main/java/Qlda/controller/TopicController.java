package Qlda.controller;

import Qlda.dto.request.TopicRequest;
import Qlda.dto.response.ApiResponse;
import Qlda.dto.response.TopicResponse;
import Qlda.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin(origins = "*")
public class TopicController {
    private final TopicService service;

    public TopicController(TopicService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<TopicResponse>> getAll() {
        return ApiResponse.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<TopicResponse> getById(@PathVariable Long id) {
        return ApiResponse.ok(service.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TopicResponse> create(@Valid @RequestBody TopicRequest request) {
        return ApiResponse.ok("Tạo mới thành công", service.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<TopicResponse> update(@PathVariable Long id, @Valid @RequestBody TopicRequest request) {
        return ApiResponse.ok("Cập nhật thành công", service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok("Xóa thành công", null);
    }
}