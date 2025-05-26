package com.getir.clone.backend.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank(message = "İsim boş olamaz")
        String fullName
) {}
