package com.example.vet_mvc_app.authentication.JWT;

import com.example.vet_mvc_app.users.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
    public String generateToken(Long userId, String userName, String email, User.Role role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("id", userId)
                .claim("userName", userName)
                .claim("role", role)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

/*    public UserResponse getUserDataFromToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        assert auth != null;
        return (UserResponse) auth.getPrincipal();
    }*/
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}