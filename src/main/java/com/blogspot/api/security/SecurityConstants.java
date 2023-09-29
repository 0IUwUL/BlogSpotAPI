package com.blogspot.api.security;

import java.security.Key;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class SecurityConstants {
    public static final long JWT_Expiration = 7000;
    public static final Key JWT_Secret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
}
