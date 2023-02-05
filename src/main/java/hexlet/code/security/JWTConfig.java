package hexlet.code.security;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;


@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
public record JWTConfig(
        String issuer,
        Long expirationSec,
        Long clockSkewSec,
        String secret
) { }

