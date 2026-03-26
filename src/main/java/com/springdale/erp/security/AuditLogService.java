package com.springdale.erp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Simple audit service. In a larger system this can be redirected to
 * a database table, SIEM or event bus.
 */
@Service
public class AuditLogService {

    private static final Logger log = LoggerFactory.getLogger(AuditLogService.class);

    public void logLoginSuccess(String email) {
        log.info("AUDIT login-success email={}", email);
    }

    public void logLoginFailure(String email) {
        log.warn("AUDIT login-failure email={}", email);
    }

    public void logAction(String actor, String action, String entityType, Object entityId) {
        log.info("AUDIT actor={} action={} entityType={} entityId={}", actor, action, entityType, entityId);
    }
}
