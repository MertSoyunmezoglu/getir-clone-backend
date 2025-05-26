package com.getir.clone.backend.exceptions;


public class UserNotFoundException extends BackendException {
    public UserNotFoundException() {
        super("Kullanıcı bulunamadı.");
    }
}
