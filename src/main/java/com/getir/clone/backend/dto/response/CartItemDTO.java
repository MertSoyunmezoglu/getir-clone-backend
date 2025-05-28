package com.getir.clone.backend.dto.response;

public record CartItemDTO(
        Long productId,
        String productName,
        int quantity
) { }
