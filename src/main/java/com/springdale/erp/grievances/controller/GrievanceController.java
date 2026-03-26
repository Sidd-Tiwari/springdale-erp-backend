package com.springdale.erp.grievances.controller;

import com.springdale.erp.common.api.ApiResponse;
import com.springdale.erp.grievances.dto.GrievanceDto;
import com.springdale.erp.grievances.service.GrievanceService;
import com.springdale.erp.security.UserPrincipal;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grievances")
public class GrievanceController {

    private final GrievanceService grievanceService;

    public GrievanceController(GrievanceService grievanceService) {
        this.grievanceService = grievanceService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY','STUDENT')")
    public ResponseEntity<ApiResponse<GrievanceDto>> create(@AuthenticationPrincipal UserPrincipal principal,
                                                            @Valid @RequestBody GrievanceDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Grievance created successfully",
                        grievanceService.create(principal.getId(), request)));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<GrievanceDto>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Grievances fetched successfully", grievanceService.getAll()));
    }

    @GetMapping("/mine")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY','STUDENT')")
    public ResponseEntity<ApiResponse<List<GrievanceDto>>> getMine(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.success("My grievances fetched successfully",
                grievanceService.getMine(principal.getId())));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ApiResponse<GrievanceDto>> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(ApiResponse.success("Grievance status updated successfully",
                grievanceService.updateStatus(id, status)));
    }
}
