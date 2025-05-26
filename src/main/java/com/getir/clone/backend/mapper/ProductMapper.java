package com.getir.clone.backend.mapper;

import com.getir.clone.backend.dto.request.ProductCreateRequest;
import com.getir.clone.backend.dto.response.ProductDTO;
import com.getir.clone.backend.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(Product product);
    Product toEntity(ProductCreateRequest request);
    void updateProductFromRequest(ProductCreateRequest request, @MappingTarget Product product);
}
