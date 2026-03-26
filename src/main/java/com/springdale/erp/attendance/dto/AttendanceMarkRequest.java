package com.springdale.erp.attendance.dto;

import com.springdale.erp.attendance.enums.AttendanceStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record AttendanceMarkRequest(
        @NotNull LocalDate attendanceDate,
        @NotEmpty @Valid List<AttendanceItem> records
) {
    public record AttendanceItem(
            @NotNull Long studentId,
            @NotNull AttendanceStatus status,
            String remarks
    ) {}
}
