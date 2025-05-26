package com.getir.clone.backend.service.interfaces;

import com.getir.clone.backend.dto.request.ProductCreateRequest;
import com.getir.clone.backend.dto.request.ProductUpdateRequest;
import com.getir.clone.backend.dto.response.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductCreateRequest request);
    List<ProductDTO> getAllProducts();
    ProductDTO updateProduct(ProductUpdateRequest request);
    void deleteProduct(Long id);

}
