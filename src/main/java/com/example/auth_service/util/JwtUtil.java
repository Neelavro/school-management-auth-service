package com.example.auth_service.util;

import com.example.auth_service.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // TODO: Move this to application.properties for production
    private final String SECRET_KEY = "22349a84-1f39-4265-802e-87dbc47c90ef";

    private final long EXPIRATION = 1000L * 60 * 60 * 24; // 24 hours

    // Create signing key
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // Generate token
    public String generateToken(User user) {
        String roleName = user.getRole().getName(); // if you have role enum or name
        String token = Jwts.builder()
                .setSubject(user.getPhone())
                .claim("userId", user.getId())
                .claim("role", roleName) // just the name, not the entity
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        return  token;
    }

    // Extract claims
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Validate token
    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // Extract specific info if needed
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public Long extractUserId(String token) {
        return ((Number) extractClaims(token).get("userId")).longValue();
    }

    public String extractRole(String token) {
        return (String) extractClaims(token).get("role");
    }
}
