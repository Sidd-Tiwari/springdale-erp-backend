package com.springdale.erp.auth.service;

import com.springdale.erp.auth.dto.ChangePasswordRequest;
import com.springdale.erp.auth.dto.ForgotPasswordRequest;
import com.springdale.erp.auth.dto.LoginRequest;
import com.springdale.erp.auth.dto.LoginResponse;
import com.springdale.erp.auth.dto.RefreshTokenRequest;
import com.springdale.erp.common.exception.UnauthorizedException;
import com.springdale.erp.security.AuditLogService;
import com.springdale.erp.security.JwtService;
import com.springdale.erp.security.UserPrincipal;
import com.springdale.erp.users.entity.User;
import com.springdale.erp.users.repo.UserRepository;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authentication orchestration service.
 */
@Service
@Transactional
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditLogService auditLogService;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuditLogService auditLogService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.auditLogService = auditLogService;
    }

    // ✅ FIXED LOGIN METHOD
    public LoginResponse login(LoginRequest request) {
        try {
            // 🔥 Correct authentication token
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );

            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

            User user = userRepository.findById(principal.getId())
                    .orElseThrow(() -> new UnauthorizedException("User not found"));

            user.setLastLoginAt(Instant.now());

            auditLogService.logLoginSuccess(request.email());

            return new LoginResponse(
                    jwtService.generateAccessToken(principal),
                    jwtService.generateRefreshToken(principal),
                    new LoginResponse.UserSummary(
                            user.getId(),
                            user.getFullName(),
                            user.getEmail(),
                            user.getRole()
                    )
            );

        } catch (AuthenticationException ex) {
            auditLogService.logLoginFailure(request.email());
            throw new UnauthorizedException("Invalid email or password");
        }
    }

    public LoginResponse refresh(RefreshTokenRequest request) {
        Jwt jwt = jwtService.decode(request.refreshToken());

        if (!jwtService.isRefreshToken(jwt)) {
            throw new UnauthorizedException("Invalid refresh token");
        }

        User user = userRepository.findByEmailIgnoreCase(jwt.getSubject())
                .orElseThrow(() -> new UnauthorizedException("User no longer exists"));

        UserPrincipal principal = UserPrincipal.from(user);

        return new LoginResponse(
                jwtService.generateAccessToken(principal),
                jwtService.generateRefreshToken(principal),
                new LoginResponse.UserSummary(
                        user.getId(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getRole()
                )
        );
    }

    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("Current password is incorrect");
        }

        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));

        auditLogService.logAction(user.getEmail(), "CHANGE_PASSWORD", "USER", userId);
    }

    public Map<String, String> forgotPassword(ForgotPasswordRequest request) {
        userRepository.findByEmailIgnoreCase(request.email()).ifPresent(user -> {
            String traceToken = UUID.randomUUID().toString();
            log.info("Password reset requested for email={} traceToken={}", request.email(), traceToken);

            auditLogService.logAction(request.email(), "FORGOT_PASSWORD_REQUEST", "USER", user.getId());
        });

        return Map.of("message", "If the account exists, password reset instructions will be sent.");
    }

    @Transactional(readOnly = true)
    public LoginResponse.UserSummary currentUser(UserPrincipal principal) {
        User user = userRepository.findById(principal.getId())
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        return new LoginResponse.UserSummary(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        );
    }
}