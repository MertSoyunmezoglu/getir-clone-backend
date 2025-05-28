package com.getir.clone.backend.exceptions;

public class InsufficientStockException  extends BackendException {
    public InsufficientStockException() {
        super("Yetersiz stok");
    }
}
