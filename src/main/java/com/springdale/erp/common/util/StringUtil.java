package com.springdale.erp.common.util;

public final class StringUtil {

    private StringUtil() {
    }

    public static boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static String normalize(String value) {
        return value == null ? null : value.trim();
    }
}
