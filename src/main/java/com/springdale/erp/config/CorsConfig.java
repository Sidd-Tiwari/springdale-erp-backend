package com.springdale.erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Central CORS configuration used by Spring Security.
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource(AppProperties appProperties) {
        var config = new org.springframework.web.cors.CorsConfiguration();
        config.setAllowedOrigins(appProperties.getCors().getAllowedOrigins());
        config.setAllowedMethods(appProperties.getCors().getAllowedMethods());
        config.setAllowedHeaders(appProperties.getCors().getAllowedHeaders());
        config.setAllowCredentials(appProperties.getCors().isAllowCredentials());
        config.setMaxAge(appProperties.getCors().getMaxAge());

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
