package com.example.rtapservice.controller;

import com.example.rtapservice.dto.auth.AuthRequest;
import com.example.rtapservice.dto.auth.AuthResponse;
import com.example.rtapservice.security.CustomUserDetailsService;
import com.example.rtapservice.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for generating JWT tokens")
public class AuthController {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Operation(summary = "Demo Login", description = "Generates a JWT for an existing user. No password required for this demo environment.")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        // 1. Fetch user details (this will throw an exception if the user doesn't exist)
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());

        // 2. Generate the token containing the user's role
        String token = jwtService.generateToken(userDetails);

        // 3. Return the token to the client
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
