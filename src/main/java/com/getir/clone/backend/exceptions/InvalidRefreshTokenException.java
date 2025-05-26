package com.getir.clone.backend.exceptions;

public class InvalidRefreshTokenException extends BackendException {
    public InvalidRefreshTokenException() {
        super("Refresh token geçersiz, süresi dolmuş ya da eşleşmedi.");
    }
}
