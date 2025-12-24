package com.sweet.acl_jwt.component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value(value = "${jwt.secretKey}")
    private String secretKey;

    @Value(value = "${jwt.expiration}")
    private Long expiration;

    public String generate(String username) {
        try{
            return Jwts.builder()
                    .setSubject(username)
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(createSecretKey(), SignatureAlgorithm.HS256) // need bytes (convert from secretKey)
                    .compact();
        }
        catch (Exception e){
            throw new JwtException(e.getMessage());
        }
    }

    public Key createSecretKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    public Claims extractAllClaims(String token){
        try{
            // Sử dụng secretKey để giải mã token, rồi lây dữ liệu ra
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (SignatureException se){
            throw new JwtException("invalid token signature");
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenExpired(String token){
        Date expiration = this.extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    public String extractUsername(String token){
        return this.extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails user){
        return !this.isTokenExpired(token);
    }
}
