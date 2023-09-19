package com.blogspot.api.security;

import java.util.Date;


import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;


@Component
public class TokenGenerator {
    
    public String generateToken(Authentication auth){
        String username = auth.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + SecurityConstants.JWT_Expiration);

        String token = Jwts.builder()
                            .setSubject(username)
                            .setIssuedAt(new Date())
                            .setExpiration(expiryDate)
                            .signWith(SecurityConstants.JWT_Secret)
                            .compact();
        return token;
    }

    public String getUsernameFromJWT(String token){
        JwtParser parser = Jwts.parserBuilder()
                            .setSigningKey(SecurityConstants.JWT_Secret)
                            .build();
        Jws<Claims> jws = parser.parseClaimsJws(token);
        Claims claims = jws.getBody();
        return claims.getSubject();
    }

    public Boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(SecurityConstants.JWT_Secret).build().parseClaimsJws(token);
            return true;
        } catch(Exception ex){
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or invalid");
        }
    }    
}
