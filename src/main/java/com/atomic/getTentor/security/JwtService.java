package com.atomic.getTentor.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atomic.getTentor.model.AbstractMahasiswa;
import com.atomic.getTentor.model.Tentor;
import com.atomic.getTentor.repository.TentorRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {
    private final Key key;
    private final JwtConfig jwtConfig;

    @Autowired
    TentorRepository tentorRepository;

    @Autowired
    public JwtService(Key key, JwtConfig jwtConfig) {
        this.key = key;
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(AbstractMahasiswa user) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("nama", user.getNama())
                .claim("role", user.getRole());

        if ("tentor".equalsIgnoreCase(user.getRole())) {
            Tentor tentor = tentorRepository.findByMahasiswaEmail(user.getEmail());
            if (tentor != null && tentor.getFotoUrl() != null) {
                builder.claim("fotoUrl", tentor.getFotoUrl());
            }
        }

        builder.setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(key);

        return builder.compact();
    }

    public String generateResetPasswordToken(String email) {
        return Jwts.builder()
                .setSubject("reset-password"+email)
                .claim("email", email)
                .claim("type", "reset")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 10 menit
                .signWith(key)
                .compact();
    }


    public boolean isResetPasswordToken(String token) {
        Claims claims = extractAllClaims(token);
        String type = claims.get("type", String.class);
        return "reset".equals(type);
    }


    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getRoleFromToken(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public String getNamaFromToken(String token) {
        return extractAllClaims(token).get("nama", String.class);
    }

    public String getEmailFromToken(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;  // Jika error, token tidak valid
        }
    }
}
