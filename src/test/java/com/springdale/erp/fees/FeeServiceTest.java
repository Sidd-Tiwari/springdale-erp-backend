package com.springdale.erp.fees;

import static org.assertj.core.api.Assertions.assertThat;
import com.springdale.erp.fees.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

class FeeServiceTest {

    @Test
    void enumShouldContainPaid() {
        assertThat(PaymentStatus.valueOf("PAID")).isEqualTo(PaymentStatus.PAID);
    }
}
