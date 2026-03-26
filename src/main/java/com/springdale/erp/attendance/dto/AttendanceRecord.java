package com.springdale.erp.attendance.dto;

import com.springdale.erp.attendance.enums.AttendanceStatus;
import java.time.LocalDate;

public record AttendanceRecord(
        Long id,
        Long studentId,
        String studentName,
        String admissionNo,
        LocalDate attendanceDate,
        AttendanceStatus status,
        String remarks
) {
}
