package com.springdale.erp.students.controller;

import com.springdale.erp.common.api.ApiResponse;
import com.springdale.erp.common.api.PageResponse;
import com.springdale.erp.students.dto.*;
import com.springdale.erp.students.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Student CRUD and search APIs.
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ApiResponse<StudentDto>> create(@Valid @RequestBody StudentCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Student created successfully", studentService.create(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY') or #id == authentication.principal.id")
    public ResponseEntity<ApiResponse<StudentDto>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Student fetched successfully", studentService.get(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ApiResponse<StudentDto>> update(@PathVariable Long id,
                                                          @Valid @RequestBody StudentUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Student updated successfully", studentService.update(id, request)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ApiResponse<PageResponse<StudentDto>>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String section,
            @RequestParam(required = false) String academicYear,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        StudentSearchRequest request = new StudentSearchRequest(keyword, className, section, academicYear, active,
                page, size, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success("Students fetched successfully", studentService.search(request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Student deleted successfully", null));
    }
}
