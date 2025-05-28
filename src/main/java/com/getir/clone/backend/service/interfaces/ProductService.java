package com.getir.clone.backend.service.interfaces;

import com.getir.clone.backend.dto.request.ProductCreateRequest;
import com.getir.clone.backend.dto.request.ProductUpdateRequest;
import com.getir.clone.backend.dto.response.ProductDTO;
import com.getir.clone.backend.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDTO createProduct(ProductCreateRequest request);
    List<ProductDTO> getAllProducts();
    ProductDTO updateProduct(ProductUpdateRequest request);
    void deleteProduct(Long id);
    Optional<Product> getById(Long id);
    void decreaseStock(Long productId, int quantity);
    void increaseStock(Long productId, int quantity);
}
