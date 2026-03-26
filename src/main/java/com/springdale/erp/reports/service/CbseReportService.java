package com.springdale.erp.reports.service;

import com.springdale.erp.students.repo.StudentRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CbseReportService {

    private final StudentRepository studentRepository;

    public CbseReportService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Map<String, Object> studentStrengthReport() {
        long totalStudents = studentRepository.count();

        List<Map<String, Object>> byClass = studentRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        student -> student.getClassName() + "-" + student.getSection(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .map(entry -> Map.<String, Object>of(
                        "classSection", entry.getKey(),
                        "count", entry.getValue()
                ))
                .toList();

        return Map.of(
                "totalStudents", totalStudents,
                "classStrength", byClass
        );
    }
}