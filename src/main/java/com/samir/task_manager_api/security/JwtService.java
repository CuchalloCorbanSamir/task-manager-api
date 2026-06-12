package com.samir.task_manager_api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;

@Service
public class JwtService {
/*
        private static final String SECRET_KEY =
                "MiClaveSuperSecretaParaJwt2026TaskManagerApi";

        private final Key key =
                Keys.hmacShaKeyFor(
                        SECRET_KEY.getBytes(
                                StandardCharsets.UTF_8));
*/
/* */
        @Value("${jwt.secret}")
        private String secretKey;

        private Key key;

        @PostConstruct
        public void init() {

                key = Keys.hmacShaKeyFor(
                        secretKey.getBytes(
                                StandardCharsets.UTF_8));
        }
/* */
        public String generateToken(
                        String username,
                        String role) {

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 86400000))
                .signWith(key)
                .compact();
        }

        public String extractUsername(String token) {

                return Jwts.parser()
                        .verifyWith((javax.crypto.SecretKey) key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getSubject();
        }

        public boolean isTokenValid(String token) {

                try {

                Jwts.parser()
                        .verifyWith((javax.crypto.SecretKey) key)
                        .build()
                        .parseSignedClaims(token);

                return true;

                } catch (Exception e) {

                return false;
                }
        }

        public String extractRole(String token) {

        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
        }

        public String generateRefreshToken(
        String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 604800000))
                .signWith(key)
                .compact();
        }

}