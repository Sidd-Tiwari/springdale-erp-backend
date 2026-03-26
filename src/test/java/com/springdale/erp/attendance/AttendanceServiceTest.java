package com.springdale.erp.attendance;

import static org.assertj.core.api.Assertions.assertThat;
import com.springdale.erp.attendance.enums.AttendanceStatus;
import org.junit.jupiter.api.Test;

class AttendanceServiceTest {

    @Test
    void enumShouldContainPresent() {
        assertThat(AttendanceStatus.valueOf("PRESENT")).isEqualTo(AttendanceStatus.PRESENT);
    }
}
