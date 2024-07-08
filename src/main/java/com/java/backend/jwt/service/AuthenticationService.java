package com.java.backend.jwt.service;

import com.java.backend.jwt.dto.SigninRequest;
import com.java.backend.jwt.dto.SignupRequest;
import com.java.backend.jwt.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    User signup(SignupRequest request);

    String generateToken(UserDetails userDetails);

    User authenticate(SigninRequest request);

    User findUser(String id);
}
