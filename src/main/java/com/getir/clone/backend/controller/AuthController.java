package com.getir.clone.backend.controller;

import com.getir.clone.backend.dto.request.LoginRequest;
import com.getir.clone.backend.dto.request.RefreshTokenRequest;
import com.getir.clone.backend.dto.request.RegisterRequest;
import com.getir.clone.backend.dto.response.AuthResponse;
import com.getir.clone.backend.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Kayıt ve Giriş işlemleri")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Kullanıcı Kaydı", description = "Yeni bir kullanıcı kaydı oluşturur")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        AuthResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Kullanıcı Girişi", description = "Kullanıcı giriş işlemi yapar ve JWT token döner")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh token ile yeni access token üretir")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(userService.refreshToken(request));
    }
}
