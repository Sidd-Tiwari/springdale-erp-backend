package com.springdale.erp.students.dto;

public record StudentSearchRequest(
        String keyword,
        String className,
        String section,
        String academicYear,
        Boolean active,
        int page,
        int size,
        String sortBy,
        String sortDir
) {
    public static StudentSearchRequest defaults(StudentSearchRequest request) {
        return new StudentSearchRequest(
                request.keyword(),
                request.className(),
                request.section(),
                request.academicYear(),
                request.active(),
                request.page() < 0 ? 0 : request.page(),
                request.size() <= 0 ? 10 : request.size(),
                request.sortBy() == null || request.sortBy().isBlank() ? "createdAt" : request.sortBy(),
                request.sortDir() == null || request.sortDir().isBlank() ? "desc" : request.sortDir()
        );
    }
}
