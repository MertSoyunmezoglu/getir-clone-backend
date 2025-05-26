package com.getir.clone.backend.dto.response;

public record AuthResponse(String token, String refreshToken, UserDTO user) {}
