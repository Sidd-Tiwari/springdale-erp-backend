package com.springdale.erp.attendance.dto;

public record AttendanceSummaryDto(
        Long studentId,
        String studentName,
        long presentCount,
        long absentCount,
        long lateCount,
        long leaveCount,
        long totalMarked
) {
}
