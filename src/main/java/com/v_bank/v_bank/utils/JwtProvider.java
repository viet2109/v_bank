package com.v_bank.v_bank.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtProvider {

    public String generateToken(String email) {
        Date now = new Date();
        long JWT_TOKEN_TIME_EXPIRATION = 31556926000L;
        Date expiryDate = new Date(now.getTime() + JWT_TOKEN_TIME_EXPIRATION);
        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {
        String JWT_KEY_SECRET = "alcwxtb1Qr9wUbDJ5E0J74pmODfqb3pD9az3AAbxp5wrCwipvbvZ88xNQIqDyLKY8aDzem5IbNACEtXw";
        byte[] keyBytes = Decoders.BASE64.decode(JWT_KEY_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public String extractUserEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public boolean isTokenExpiration(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userEmail = extractUserEmail(token);

        return !isTokenExpiration(token) && userEmail.equals(userDetails.getUsername());
    }


}
