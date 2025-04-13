package com.springboot.api.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private final String BASE64_SECRET_KEY = "5EE34F8872311224A4BEF6814F49F5EE34F8872311224A4BEF6814F49F";
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(BASE64_SECRET_KEY));

    public String generateToken(String username, String role) {
        // Ensure role has the "ROLE_" prefix
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }

        // Ma trận phân quyền
        // permission
        // PERMISSION_

        // Api Login => tạo ra jwt token, refresh token
        // Api refresh => tạo ra jwt token mới
        // jwt token => lưu ở cookie phía client
    
        return Jwts.builder()
                   .setSubject(username)
                   .claim("role", role)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                   .signWith(SECRET_KEY)
                   .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public String extractRole(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("role", String.class);
    }
}