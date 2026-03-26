package com.springdale.erp.reports.service;

import com.springdale.erp.exams.repo.ExamResultRepository;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExamReportService {

    private final ExamResultRepository examResultRepository;

    public ExamReportService(ExamResultRepository examResultRepository) {
        this.examResultRepository = examResultRepository;
    }

    public Map<String, Object> examPerformance(Long examId) {
        var results = examResultRepository.findByExamId(examId);
        BigDecimal totalMax = results.stream().map(r -> r.getMaxMarks()).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalObtained = results.stream().map(r -> r.getObtainedMarks()).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal avgPercentage = totalMax.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO :
                totalObtained.multiply(BigDecimal.valueOf(100)).divide(totalMax, 2, java.math.RoundingMode.HALF_UP);

        return Map.of(
                "examId", examId,
                "resultEntries", results.size(),
                "overallPercentage", avgPercentage
        );
    }
}
