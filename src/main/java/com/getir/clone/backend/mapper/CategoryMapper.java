package com.getir.clone.backend.mapper;

import com.getir.clone.backend.dto.response.CategoryDTO;
import com.getir.clone.backend.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDto(Category category);
    List<CategoryDTO> toDtoList(List<Category> categories);
}
