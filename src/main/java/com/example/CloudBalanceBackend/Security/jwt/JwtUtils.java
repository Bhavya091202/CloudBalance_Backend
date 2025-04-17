package com.example.CloudBalanceBackend.Security.jwt;

import com.example.CloudBalanceBackend.Security.dto.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwtUtil.app.jwtSecret}")
    private String jwtSecret;

    private Key getSigninKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    @Value("${jwtUtil.app.jwtAccessExpirationMs}")
    private Long jwtAccessExpirationMs;

    @Value("${jwtUtil.app.jwtRefreshExpirationMs}")
    private Long jwtRefreshExpirationMs;

    public String generateAccessToken(UserDetails userDetails) {
        UserDetailsImpl user = (UserDetailsImpl) userDetails;
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getUsername());
        claims.put("authorities", user.getAuthorities());
//        claims.put("firstName", user.getFirstName());
//        claims.put("lastName", user.getLastName());
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtAccessExpirationMs))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    public String generateRefreshToken(UserDetails userDetails) {
//        return Jwts
//                .builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshExpirationMs))
//                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }

    public String extractUsername(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Date extractExpiration(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public boolean validateToken(String token) {
        try {
            Jwts
              .parserBuilder()
              .setSigningKey(getSigninKey())
              .build()
              .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }


}
