package com.example.final1.controller;

import com.example.final1.dto.request.AmalbekMirasLoginRequest;
import com.example.final1.dto.request.AmalbekMirasRegisterRequest;
import com.example.final1.dto.response.AmalbekMirasAuthResponse;
import com.example.final1.service.AmalbekMirasAuthService;
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
public class AmalbekMirasAuthController {

    private final AmalbekMirasAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AmalbekMirasAuthResponse> register(@Valid @RequestBody AmalbekMirasRegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AmalbekMirasAuthResponse> login(@Valid @RequestBody AmalbekMirasLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
