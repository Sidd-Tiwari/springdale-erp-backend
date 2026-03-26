package com.springdale.erp.reports.controller;

import com.springdale.erp.common.api.ApiResponse;
import com.springdale.erp.reports.service.*;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Consolidated reporting APIs.
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final CbseReportService cbseReportService;
    private final AttendanceReportService attendanceReportService;
    private final FeeReportService feeReportService;
    private final ExamReportService examReportService;

    public ReportController(CbseReportService cbseReportService,
                            AttendanceReportService attendanceReportService,
                            FeeReportService feeReportService,
                            ExamReportService examReportService) {
        this.cbseReportService = cbseReportService;
        this.attendanceReportService = attendanceReportService;
        this.feeReportService = feeReportService;
        this.examReportService = examReportService;
    }

    @GetMapping("/cbse/student-strength")
    public ResponseEntity<ApiResponse<Map<String, Object>>> studentStrength() {
        return ResponseEntity.ok(ApiResponse.success("CBSE student strength report generated",
                cbseReportService.studentStrengthReport()));
    }

    @GetMapping("/attendance/daily-summary")
    public ResponseEntity<ApiResponse<Map<String, Object>>> dailyAttendanceSummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ApiResponse.success("Attendance report generated",
                attendanceReportService.dailySummary(date)));
    }

    @GetMapping("/fees/monthly-collection")
    public ResponseEntity<ApiResponse<Map<String, Object>>> monthlyCollection(@RequestParam String feeMonth) {
        return ResponseEntity.ok(ApiResponse.success("Fee report generated",
                feeReportService.monthlyCollection(feeMonth)));
    }

    @GetMapping("/exams/performance")
    public ResponseEntity<ApiResponse<Map<String, Object>>> examPerformance(@RequestParam Long examId) {
        return ResponseEntity.ok(ApiResponse.success("Exam report generated",
                examReportService.examPerformance(examId)));
    }
}
