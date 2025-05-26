package com.getir.clone.backend.exceptions;

import java.time.LocalDateTime;

public record ApiError(
        String code,
        String message,
        LocalDateTime timestamp
) {
    public ApiError(String code, String message) {
        this(code, message, LocalDateTime.now());
    }
}
