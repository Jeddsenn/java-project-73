package hexlet.code.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "jwt")
@Configuration
public class JWTInitialiser {
    @Value("${jwt.issuer:meeeh}") String issuer;
    @Value("${jwt.expiration-sec:86400}") Long expirationSec;
    @Value("${jwt.clock-skew-sec:10}") Long clockSkewSec;
    @Value("${jwt.secret:secret}") String secret;
}
