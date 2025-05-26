package com.getir.clone.backend.exceptions;

public class InvalidPasswordException extends BackendException {
    public InvalidPasswordException() {
        super("Şifre yanlış.");
    }
}
