package com.getir.clone.backend.service.impl;

import com.getir.clone.backend.dto.response.CategoryDTO;
import com.getir.clone.backend.entity.Category;
import com.getir.clone.backend.exceptions.CategoryNotFoundException;
import com.getir.clone.backend.mapper.CategoryMapper;
import com.getir.clone.backend.repository.CategoryRepository;
import com.getir.clone.backend.service.interfaces.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public Set<Category> getCategoriesByIds(List<Long> ids) {
        return ids.stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(CategoryNotFoundException::new))
                .collect(Collectors.toSet());
    }

}
