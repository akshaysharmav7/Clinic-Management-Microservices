package com.clinic.authservice.controller;

import com.clinic.authservice.service.JwtService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        // Temporary hardcoded user for learning.
        // We will replace this with a database later.
        if (!"admin".equals(request.username())
                || !"password".equals(request.password())) {

            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(
                request.username(),
                "ADMIN"
        );

        return new LoginResponse(token);
    }

    public record LoginRequest(
            String username,
            String password
    ) {}

    public record LoginResponse(
            String token
    ) {}
}