package com.getir.clone.backend.exceptions;

public abstract class BackendException extends RuntimeException {
    protected BackendException(String message) {
        super(message);
    }
}
