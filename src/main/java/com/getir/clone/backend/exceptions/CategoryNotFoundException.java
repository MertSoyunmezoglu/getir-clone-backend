package com.getir.clone.backend.exceptions;

public class CategoryNotFoundException extends BackendException {
    public CategoryNotFoundException() {
        super("Bu kategoriye ait ürün bulunamadı");
    }
}
