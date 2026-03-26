package com.springdale.erp.auth.dto;

import com.springdale.erp.users.enums.Role;

public record LoginResponse(
        String token,
        String refreshToken,
        UserSummary user
) {
    public record UserSummary(Long id, String fullName, String email, Role role) {}
}
