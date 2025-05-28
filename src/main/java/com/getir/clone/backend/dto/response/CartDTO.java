package com.getir.clone.backend.dto.response;

import java.util.List;

public record CartDTO(
        Long id,
        List<CartItemDTO> items,
        boolean checkedOut
) { }

