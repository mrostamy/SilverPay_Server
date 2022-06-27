package com.mydomomain.silverpay.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Component
public class Jwt {

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String jwtGeneration(String username, String userId, boolean remember) {

        Date expire = remember ? Date.from(Instant.now().plus(2, ChronoUnit.DAYS)) :
                Date.from(Instant.now().plus(2, ChronoUnit.HOURS));

        return Jwts.builder()
                .setSubject(String.format("%s,%s", userId, username))
                .setIssuer("mohammad")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("JWT error: " + e.getMessage());
            return false;
        }

    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {

        return Jwts.parser().
                setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

    }


}
