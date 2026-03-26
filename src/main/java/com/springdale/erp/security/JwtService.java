package com.springdale.erp.security;

import com.springdale.erp.config.AppProperties;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    public static final String TOKEN_TYPE_ACCESS = "access";
    public static final String TOKEN_TYPE_REFRESH = "refresh";

    private final AppProperties appProperties;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtService(AppProperties appProperties) {
        this.appProperties = appProperties;

        SecretKeySpec key = new SecretKeySpec(
                appProperties.getJwt().getSecret().getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );

        this.jwtEncoder = new NimbusJwtEncoder(
                new com.nimbusds.jose.jwk.source.ImmutableSecret<>(key)
        );

        this.jwtDecoder = NimbusJwtDecoder.withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    public String generateAccessToken(UserPrincipal principal) {
        return encode(principal, TOKEN_TYPE_ACCESS, appProperties.getJwt().getAccessTokenTtl().toSeconds());
    }

    public String generateRefreshToken(UserPrincipal principal) {
        return encode(principal, TOKEN_TYPE_REFRESH, appProperties.getJwt().getRefreshTokenTtl().toSeconds());
    }

    private String encode(UserPrincipal principal, String tokenType, long ttlSeconds) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(appProperties.getJwt().getIssuer())
                .subject(principal.getUsername())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(ttlSeconds))
                .claim("uid", principal.getId())
                .claim("role", principal.getRole().name())
                .claim("name", principal.getFullName())
                .claim("type", tokenType)
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS256).build(),
                        claims
                )
        ).getTokenValue();
    }

    public Jwt decode(String token) {
        return jwtDecoder.decode(token);
    }

    public boolean isAccessToken(Jwt jwt) {
        return Objects.equals(TOKEN_TYPE_ACCESS, jwt.getClaimAsString("type"));
    }

    public boolean isRefreshToken(Jwt jwt) {
        return Objects.equals(TOKEN_TYPE_REFRESH, jwt.getClaimAsString("type"));
    }
}