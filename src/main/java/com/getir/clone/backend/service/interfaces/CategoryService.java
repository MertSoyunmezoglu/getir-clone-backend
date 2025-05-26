package com.getir.clone.backend.service.interfaces;

import com.getir.clone.backend.dto.response.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
}
