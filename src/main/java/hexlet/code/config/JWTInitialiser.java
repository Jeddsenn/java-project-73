package hexlet.code.config;

import org.springframework.beans.factory.annotation.Value;

public record
JWTInitialiser(
        @Value("${jwt.issuer:meeeh}") String issuer,
        @Value("${jwt.expiration-sec:86400}") Long expirationSec,
        @Value("${jwt.clock-skew-sec:10}") Long clockSkewSec,
        @Value("${jwt.secret:secret}") String secret) {
}
