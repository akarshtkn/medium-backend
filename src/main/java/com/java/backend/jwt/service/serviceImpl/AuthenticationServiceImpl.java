package com.java.backend.jwt.service.serviceImpl;

import com.java.backend.commons.exceptions.UserNotFoundException;
import com.java.backend.jwt.dto.SigninRequest;
import com.java.backend.jwt.dto.SignupRequest;
import com.java.backend.jwt.entity.User;
import com.java.backend.jwt.repository.UserRepository;
import com.java.backend.jwt.service.AuthenticationService;
import com.java.backend.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Override
    public User signup(SignupRequest request) {
        logger.info("Executing business logic to register a user");
        logger.info("email :{}", request.getEmail());
        logger.info("password :{}", request.getPassword());
        logger.info("name :{}", request.getName());
        User user = User.builder()
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();
        return userRepository.save(user);
    }

    public String generateToken(UserDetails userDetails) {
        logger.info("Executing business logic to generate token with user details");
        return jwtService.generateToken(userDetails);
    }

    public User authenticate(SigninRequest request) {
        logger.info("Executing business logic to authenticate the user");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        logger.info("Executing business logic to fetch user details of user with email :{}", request.getEmail());
        return userRepository.findByUsername(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(request.getEmail()));
    }

    @Override
    public User findUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}
