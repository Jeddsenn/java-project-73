package hexlet.code.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;


@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
public record JWTConfig(
        @Value("${jwt.issuer:meeeh}")  String issuer,
        @Value("${jwt.expiration-sec:86400}")  Long expirationSec,
        @Value("${jwt.clock-skew-sec:10}")  Long clockSkewSec,
        @Value("${jwt.secret:secret}")  String secret
) { }

