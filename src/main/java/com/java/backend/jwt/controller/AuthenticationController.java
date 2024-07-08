package com.java.backend.jwt.controller;

import com.java.backend.jwt.dto.SigninRequest;
import com.java.backend.jwt.dto.SignupRequest;
import com.java.backend.jwt.dto.SignupResponse;
import com.java.backend.jwt.entity.User;
import com.java.backend.jwt.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/api/v1/user")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    @Value("${spring.security.jwt.cookieExpiry}")
    private long cookieExpiry;

    private final AuthenticationService authenticationService;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request,
                                                 HttpServletResponse response){
        logger.info("Executing signup endpoint");
        User user = authenticationService.signup(request);
        String token = authenticationService.generateToken(user);

        SignupResponse responseBody = SignupResponse.builder()
                .id(user.getId())
                .email(user.getUsername())
                .name(user.getName())
                .token(token)
                .build();

        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(cookieExpiry)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PostMapping("/signin")
    public ResponseEntity<SignupResponse> signIn(@RequestBody SigninRequest request,
                                                  HttpServletResponse response){
        logger.info("Executing signin endpoint");
        User user = authenticationService.authenticate(request);
        String token = authenticationService.generateToken(user);

        SignupResponse responseBody = SignupResponse.builder()
                .id(user.getId())
                .email(user.getUsername())
                .name(user.getName())
                .token(token)
                .build();

        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(cookieExpiry)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        logger.info("Logout endpoint");

        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        logger.info("Cookie cleared");

        return ResponseEntity.status(HttpStatus.OK).body("User logged out");
    }
}