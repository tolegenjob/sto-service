package com.example.stoservice.controller;

import com.example.stoservice.dto.request.LoginRequest;
import com.example.stoservice.dto.request.RegisterRequest;
import com.example.stoservice.dto.response.LoginResponse;
import com.example.stoservice.dto.response.RegisterResponse;
import com.example.stoservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        RegisterResponse response = RegisterResponse.toDto(authService.register(request));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        String token = authService.login(request);
        LoginResponse response = new LoginResponse(request.email(), token);
        return ResponseEntity.ok(response);
    }

}
