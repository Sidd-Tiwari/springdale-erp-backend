package com.springdale.erp.fees.controller;

import com.springdale.erp.common.api.ApiResponse;
import com.springdale.erp.fees.dto.*;
import com.springdale.erp.fees.service.FeeService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fees")
public class FeeController {

    private final FeeService feeService;

    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @PostMapping("/structures")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<FeeStructureDto>> upsertStructure(@Valid @RequestBody FeeStructureDto request) {
        return ResponseEntity.ok(ApiResponse.success("Fee structure saved successfully", feeService.upsertStructure(request)));
    }

    @GetMapping("/structures")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ApiResponse<List<FeeStructureDto>>> getStructures() {
        return ResponseEntity.ok(ApiResponse.success("Fee structures fetched successfully", feeService.getStructures()));
    }

    @PostMapping("/payments")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ApiResponse<FeePaymentDto>> recordPayment(@Valid @RequestBody FeePaymentRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Fee payment recorded successfully", feeService.recordPayment(request)));
    }

    @GetMapping("/payments/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY') or #studentId == authentication.principal.id")
    public ResponseEntity<ApiResponse<List<FeePaymentDto>>> getPaymentsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success("Fee payments fetched successfully", feeService.getPaymentsByStudent(studentId)));
    }

    @GetMapping("/defaulters")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ApiResponse<List<FeeDefaulterDto>>> getDefaulters(
            @RequestParam String feeMonth,
            @RequestParam String className,
            @RequestParam String academicYear) {
        return ResponseEntity.ok(ApiResponse.success("Fee defaulters fetched successfully",
                feeService.getDefaulters(feeMonth, className, academicYear)));
    }
}
