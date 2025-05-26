package com.getir.clone.backend.service.impl;

import com.getir.clone.backend.dto.response.CategoryDTO;
import com.getir.clone.backend.mapper.CategoryMapper;
import com.getir.clone.backend.repository.CategoryRepository;
import com.getir.clone.backend.service.interfaces.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }
}
