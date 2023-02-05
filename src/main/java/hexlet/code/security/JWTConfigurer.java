package hexlet.code.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.impl.TextCodec.BASE64;

@EnableConfigurationProperties(value = JWTConfig.class)
@Configuration
@Component
public class JWTConfigurer {

    private static final Duration MILLIS_IN_SECOND = Duration.ofMillis(1000);
    private final String secretKey;
    private final String issuer;
    private final Long expirationSec;
    private final Long clockSkewSec;
    private final Clock clock;


    public JWTConfigurer(JWTConfig jwtConfig) {
        this.secretKey = BASE64.encode(jwtConfig.secret());
        this.issuer = jwtConfig.issuer();
        this.expirationSec = jwtConfig.expirationSec();
        this.clockSkewSec = jwtConfig.clockSkewSec();
        this.clock = DefaultClock.INSTANCE;
    }

    public final String expiring(final Map<String, Object> attributes) {
        return Jwts.builder()
                .signWith(HS256, secretKey)
                .setClaims(getClaims(attributes, expirationSec))
                .compact();
    }

    public final Map<String, Object> verify(final String token) {
        return Jwts.parser()
                .requireIssuer(issuer)
                .setClock(clock)
                .setAllowedClockSkewSeconds(clockSkewSec)
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private Claims getClaims(final Map<String, Object> attributes, final Long expiresInSec) {
        final Claims claims = Jwts.claims();
        claims.setIssuer(issuer);
        claims.setIssuedAt(clock.now());
        claims.putAll(attributes);
        if (expiresInSec > 0) {
            claims.setExpiration(new Date(System.currentTimeMillis() + expiresInSec * MILLIS_IN_SECOND.getSeconds()));
        }
        return claims;
    }

}
