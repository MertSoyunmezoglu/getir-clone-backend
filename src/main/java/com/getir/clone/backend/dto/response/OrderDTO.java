package com.getir.clone.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(
        Long id,
        List<OrderItemDTO> items,
        BigDecimal totalPrice,
        BigDecimal totalDiscountedPrice,
        String status,
        LocalDateTime createdAt
) { }
