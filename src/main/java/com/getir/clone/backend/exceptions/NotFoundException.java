package com.getir.clone.backend.exceptions;

public class NotFoundException extends BackendException {
    public NotFoundException() {
        super("Ürün bulunamadı");
    }
}
