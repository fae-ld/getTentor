package com.atomic.getTentor.security;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Configuration
// This class is responsible for configuring JWT settings
public class JwtConfig {
    @Value("${jwt.expiration:3600000}")
    private long expiration; // 1 hour in milliseconds

    @Bean
    public Key key() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public long getExpiration() {
        return expiration;
    }
}
