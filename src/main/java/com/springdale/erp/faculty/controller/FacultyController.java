package com.springdale.erp.faculty.controller;

import com.springdale.erp.common.api.ApiResponse;
import com.springdale.erp.faculty.dto.FacultyCreateRequest;
import com.springdale.erp.faculty.dto.FacultyDto;
import com.springdale.erp.faculty.service.FacultyService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<FacultyDto>> create(@Valid @RequestBody FacultyCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Faculty created successfully", facultyService.create(request)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ApiResponse<List<FacultyDto>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Faculty fetched successfully", facultyService.getAll()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ApiResponse<FacultyDto>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Faculty fetched successfully", facultyService.get(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        facultyService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Faculty deleted successfully", null));
    }
}
