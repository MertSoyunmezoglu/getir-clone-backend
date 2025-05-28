package com.getir.clone.backend.dto.response;

import java.util.List;

public record ProductDTO(
        Long id,
        String name,
        String description,
        Double price,
        Double discountedPrice,
        Integer stock,
        String imageUrl,
        List<CategoryDTO> categories
) {}
