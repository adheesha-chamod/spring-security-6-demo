package com.demo.springSecurityDemo.security;

import com.demo.springSecurityDemo.dto.LoginUserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration-time}")
    private long expirationTime;


    public String generateToken(LoginUserDTO loginUserDTO) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "USER");     // for simplicity, hardcoding the role here

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(loginUserDTO.getUsername())
                .issuer(issuer)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .and()
                .signWith(getSecretKey())
                .compact();
    }

    private SecretKey getSecretKey() {
        // extend the key to ensure it meets the length of 32 bytes
        StringBuilder extendedKey = new StringBuilder(secretKey);
        while (extendedKey.length() < 32) {
            extendedKey.append("0");
        }
        return Keys.hmacShaKeyFor(extendedKey.toString().getBytes());
    }

    public String extractUsername(String jwt) {
        return extractClaims(jwt, Claims::getSubject);
    }

    public Date extractExpiration(String jwt) {
        return extractClaims(jwt, Claims::getExpiration);
    }

    public boolean isValidateToken(String jwt, CustomUserDetails customUserDetails) {
        String username = extractUsername(jwt);
        return username.equals(customUserDetails.getUsername()) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    private <T> T extractClaims(String jwt, Function<Claims, T> claimsResolver) {
        Claims claims = extractClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaims(String jwt) {
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }
}
