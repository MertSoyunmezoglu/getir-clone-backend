package com.getir.clone.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email zorunludur")
        @Email(message = "Geçerli bir email girin")
        String email,

        @NotBlank(message = "Şifre zorunludur")
        String password
) {}
