package com.springdale.erp.common.api;

import java.util.List;
import org.springframework.data.domain.Page;

/**
 * Generic page wrapper to keep paging metadata explicit and stable.
 */
public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last,
        String sort
) {
    public static <T, R> PageResponse<R> from(Page<R> page, String sort) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                sort
        );
    }
}
