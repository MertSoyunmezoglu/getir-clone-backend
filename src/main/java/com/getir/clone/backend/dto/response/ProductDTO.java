package com.getir.clone.backend.dto.response;

import java.util.List;

public record ProductDTO(
        Long id,
        String name,
        String description,
        String imageUrl,
        double price,
       List<CategoryDTO> categories
) {}
