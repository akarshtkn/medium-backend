package com.java.backend.jwt.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String getTokenFromHeader(HttpServletRequest request);

    String extractUsername(String jwt);

    boolean isTokenExpired(String jwt);

    String generateToken(UserDetails userDetails);

    String generateToken(Map<String,Object> extraClaims, UserDetails userDetails);
}
