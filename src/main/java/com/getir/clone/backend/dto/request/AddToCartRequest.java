package com.getir.clone.backend.dto.request;

public record AddToCartRequest(
        Long productId,
        int quantity
) { }