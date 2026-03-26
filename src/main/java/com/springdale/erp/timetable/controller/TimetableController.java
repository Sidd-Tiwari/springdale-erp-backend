package com.springdale.erp.timetable.controller;

import com.springdale.erp.common.api.ApiResponse;
import com.springdale.erp.timetable.dto.TimetableEntryDto;
import com.springdale.erp.timetable.service.TimetableService;
import jakarta.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timetable")
public class TimetableController {

    private final TimetableService timetableService;

    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ApiResponse<TimetableEntryDto>> save(@Valid @RequestBody TimetableEntryDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Timetable entry saved successfully", timetableService.save(request)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY','STUDENT')")
    public ResponseEntity<ApiResponse<List<TimetableEntryDto>>> getByClassSection(@RequestParam String className,
                                                                                  @RequestParam String section,
                                                                                  @RequestParam(required = false) DayOfWeek dayOfWeek) {
        List<TimetableEntryDto> data = dayOfWeek == null
                ? timetableService.getByClassSection(className, section)
                : timetableService.getByClassSectionDay(className, section, dayOfWeek);
        return ResponseEntity.ok(ApiResponse.success("Timetable fetched successfully", data));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        timetableService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Timetable entry deleted successfully", null));
    }
}
