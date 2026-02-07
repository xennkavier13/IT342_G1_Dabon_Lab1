package com.example.backend.service;


import com.example.backend.dto.request.LoginRequest;
import com.example.backend.dto.request.RegisterRequest;
import com.example.backend.dto.response.AuthResponse;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(RegisterRequest request) {
        // Check username/email uniqueness
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        Optional<User> optionalUser = request.getIdentifier().contains("@")
                ? userRepository.findByEmail(request.getIdentifier())
                : userRepository.findByUsername(request.getIdentifier());

        if (optionalUser.isEmpty()) throw new IllegalArgumentException("Invalid credentials");

        User user = optionalUser.get();

        Instant expiration = Instant.now().plus(24, ChronoUnit.HOURS);
        String token = Jwts.builder()
                .claim("user_id", user.getUserId())
                .setExpiration(Date.from(expiration))
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
                .compact();

        return new AuthResponse(token, user.getUsername(), user.getEmail());
    }
}