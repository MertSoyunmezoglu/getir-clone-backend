package com.getir.clone.backend.service.interfaces;

import com.getir.clone.backend.dto.response.CategoryDTO;
import com.getir.clone.backend.entity.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    Set<Category> getCategoriesByIds(List<Long> ids);

}
