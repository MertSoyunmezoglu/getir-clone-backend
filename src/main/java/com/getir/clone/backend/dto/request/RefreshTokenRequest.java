package com.getir.clone.backend.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "Refresh token boş olamaz")
        String refreshToken
) {}
