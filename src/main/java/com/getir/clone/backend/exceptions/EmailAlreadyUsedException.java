package com.getir.clone.backend.exceptions;

public class EmailAlreadyUsedException extends BackendException {
    public EmailAlreadyUsedException() {
        super("Bu email ile kayıtlı kullanıcı zaten var.");
    }
}
