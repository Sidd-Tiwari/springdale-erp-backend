package com.springdale.erp.exams.controller;

import com.springdale.erp.common.api.ApiResponse;
import com.springdale.erp.exams.dto.ExamDto;
import com.springdale.erp.exams.dto.ExamResultDto;
import com.springdale.erp.exams.dto.MarksheetDto;
import com.springdale.erp.exams.service.ExamService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ApiResponse<ExamDto>> create(@Valid @RequestBody ExamDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Exam created successfully", examService.create(request)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY','STUDENT')")
    public ResponseEntity<ApiResponse<List<ExamDto>>> list(@RequestParam String className,
                                                           @RequestParam String academicYear) {
        return ResponseEntity.ok(ApiResponse.success("Exams fetched successfully", examService.list(className, academicYear)));
    }

    @PostMapping("/{examId}/results/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ApiResponse<ExamResultDto>> recordResult(@PathVariable Long examId,
                                                                   @PathVariable Long studentId,
                                                                   @Valid @RequestBody ExamResultDto request) {
        return ResponseEntity.ok(ApiResponse.success("Exam result recorded successfully",
                examService.recordResult(examId, studentId, request)));
    }

    @GetMapping("/{examId}/marksheet/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY') or #studentId == authentication.principal.id")
    public ResponseEntity<ApiResponse<MarksheetDto>> marksheet(@PathVariable Long examId, @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success("Marksheet fetched successfully",
                examService.marksheet(examId, studentId)));
    }
}
