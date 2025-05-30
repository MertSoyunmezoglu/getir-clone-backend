package com.getir.clone.backend.dto.response;

import java.math.BigDecimal;

public record OrderItemDTO(
        Long productId,
        String productName,
        int quantity,
        BigDecimal priceAtOrder,
        BigDecimal discountedPriceAtOrder
) { }

