package com.getir.clone.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProductCreateRequest(
        @NotBlank String name,
        String description,
        @NotNull Double price,
        Double discountedPrice,
        @NotNull Integer stock,
        String imageUrl,
        List<Long> categoryIds
) {}
