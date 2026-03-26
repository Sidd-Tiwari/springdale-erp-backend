package com.springdale.erp.notices.controller;

import com.springdale.erp.common.api.ApiResponse;
import com.springdale.erp.notices.dto.NoticeDto;
import com.springdale.erp.notices.service.NoticeService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ApiResponse<NoticeDto>> save(@Valid @RequestBody NoticeDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Notice saved successfully", noticeService.save(request)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ApiResponse<List<NoticeDto>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Notices fetched successfully", noticeService.getAll()));
    }

    @GetMapping("/public")
    public ResponseEntity<ApiResponse<List<NoticeDto>>> getPublicNotices() {
        return ResponseEntity.ok(ApiResponse.success("Public notices fetched successfully", noticeService.getPublicNotices()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        noticeService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Notice deleted successfully", null));
    }
}
