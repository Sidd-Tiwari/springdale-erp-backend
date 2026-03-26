package com.springdale.erp.common.util;

import java.time.LocalDate;
import java.time.YearMonth;

public final class DateUtil {

    private DateUtil() {
    }

    public static LocalDate firstDayOfMonth(String month) {
        return YearMonth.parse(month).atDay(1);
    }

    public static LocalDate lastDayOfMonth(String month) {
        return YearMonth.parse(month).atEndOfMonth();
    }
}
