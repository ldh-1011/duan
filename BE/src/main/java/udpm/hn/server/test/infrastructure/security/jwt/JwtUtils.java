package udpm.hn.server.test.infrastructure.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    // ✅ Dạng chuỗi text dài (ít nhất 64 ký tự)
    private static final String SECRET_KEY = "this_is_a_super_long_secret_key_for_jwt_signing_1234567890_abcdefghijklm";
    private static final long EXPIRATION = 1000L * 60 * 60 * 24; // 1 ngày

    private Key getSigningKey() {
        // Không decode Base64 nữa, chỉ convert sang byte
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email, String authority) {
        return Jwts.builder()
                .setSubject(email)
                .claim("auth", authority)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractAuthority(String token) {
        Object authClaim = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("auth");
        return authClaim != null ? authClaim.toString() : null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("⚠️ Token không hợp lệ: " + e.getMessage());
            return false;
        }
    }
}
