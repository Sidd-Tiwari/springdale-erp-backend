package com.springdale.erp.attendance.controller;

import com.springdale.erp.attendance.dto.AttendanceMarkRequest;
import com.springdale.erp.attendance.dto.AttendanceRecord;
import com.springdale.erp.attendance.dto.AttendanceSummaryDto;
import com.springdale.erp.attendance.service.AttendanceService;
import com.springdale.erp.common.api.ApiResponse;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/mark")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ApiResponse<List<AttendanceRecord>>> mark(@Valid @RequestBody AttendanceMarkRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Attendance marked successfully", attendanceService.markAttendance(request)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ApiResponse<List<AttendanceRecord>>> getByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ApiResponse.success("Attendance fetched successfully", attendanceService.getByDate(date)));
    }

    @GetMapping("/summary/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY') or #studentId == authentication.principal.id")
    public ResponseEntity<ApiResponse<AttendanceSummaryDto>> summary(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(ApiResponse.success("Attendance summary fetched successfully",
                attendanceService.getSummary(studentId, from, to)));
    }
}
