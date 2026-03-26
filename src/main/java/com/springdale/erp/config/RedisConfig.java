package com.springdale.erp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Placeholder Redis configuration.
 *
 * The project includes the Redis starter so that the application can be extended
 * for cache-backed rate limiting or token revocation. For now, the runtime uses
 * in-memory rate limiting unless a dedicated Redis-backed implementation is added.
 */
@Configuration
public class RedisConfig {

    private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);

    public RedisConfig() {
        log.info("Redis starter is available. Current runtime rate limiting strategy: in-memory.");
    }
}
