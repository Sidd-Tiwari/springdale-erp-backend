package com.springdale.erp.auth;

import static org.assertj.core.api.Assertions.assertThat;
import com.springdale.erp.auth.dto.LoginRequest;
import org.junit.jupiter.api.Test;

/**
 * Lightweight placeholder test to keep the test structure in place.
 * Replace with full mocked-service tests in your local environment.
 */
class AuthServiceTest {

    @Test
    void loginRequestShouldStoreValues() {
        LoginRequest request = new LoginRequest("admin@school.local", "Admin@123");
        assertThat(request.email()).isEqualTo("admin@school.local");
    }
}
