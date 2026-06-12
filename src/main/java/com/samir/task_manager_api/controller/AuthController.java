package com.samir.task_manager_api.controller;

import com.samir.task_manager_api.dto.LoginRequest;
import com.samir.task_manager_api.dto.LoginResponse;
import com.samir.task_manager_api.security.JwtService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.samir.task_manager_api.model.User;
import com.samir.task_manager_api.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public AuthController(
                JwtService jwtService,
                UserRepository userRepository,
                PasswordEncoder passwordEncoder) {

    this.jwtService = jwtService;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    }

@PostMapping("/login")
public ResponseEntity<LoginResponse> login(
        @RequestBody LoginRequest request) {
        System.out.println("VERSION NUEVA 2026-06-12");
    System.out.println("Username recibido: "
            + request.getUsername());

    User user = userRepository
            .findByUsername(
                    request.getUsername())
            .orElse(null);

    System.out.println("Usuario encontrado: "
            + (user != null));

    if (user == null) {

        return ResponseEntity
                .status(401)
                .build();
    }

    boolean passwordMatches =
            passwordEncoder.matches(
                    request.getPassword(),
                    user.getPassword());

    System.out.println("Password match: "
            + passwordMatches);

    if (!passwordMatches) {

        return ResponseEntity
                .status(401)
                .build();
    }

        String accessToken =
                jwtService.generateToken(
                        user.getUsername(),
                        user.getRole());

        String refreshToken =
                jwtService.generateRefreshToken(
                        user.getUsername());

        return ResponseEntity.ok(
                new LoginResponse(
                        accessToken,
                        refreshToken));

    }
}