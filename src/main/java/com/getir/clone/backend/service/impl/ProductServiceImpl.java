package com.getir.clone.backend.service.impl;

import com.getir.clone.backend.dto.request.ProductCreateRequest;
import com.getir.clone.backend.dto.request.ProductUpdateRequest;
import com.getir.clone.backend.dto.response.ProductDTO;
import com.getir.clone.backend.entity.Category;
import com.getir.clone.backend.entity.Product;
import com.getir.clone.backend.exceptions.InsufficientStockException;
import com.getir.clone.backend.exceptions.UserNotFoundException;
import com.getir.clone.backend.mapper.ProductMapper;
import com.getir.clone.backend.repository.ProductRepository;
import com.getir.clone.backend.service.interfaces.CategoryService;
import com.getir.clone.backend.service.interfaces.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryService = categoryService;
    }

    @Override
    public ProductDTO createProduct(ProductCreateRequest request) {
        Product product = prepareProductEntity(request);
        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductDTO updateProduct(ProductUpdateRequest request) {
        Product product = productRepository.findById(request.id())
                .orElseThrow(UserNotFoundException::new);

        productMapper.updateProductFromRequest(request, product);

        Set<Category> categories = categoryService.getCategoriesByIds(request.categoryIds());
        product.setCategories(categories);

        Product updated = productRepository.save(product);
        return productMapper.toDto(updated);
    }


    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    @Override
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void decreaseStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Ürün bulunamadı"));
        if (product.getStock() < quantity) {
            throw new InsufficientStockException();
        }
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }

    @Override
    public void increaseStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Ürün bulunamadı"));
        product.setStock(product.getStock() + quantity);
        productRepository.save(product);
    }

    private Product prepareProductEntity(ProductCreateRequest request) {
        if (request.categoryIds() == null || request.categoryIds().isEmpty()) {
            throw new IllegalArgumentException("Ürün en az bir kategoriye sahip olmalıdır.");
        }

        Product product = productMapper.toEntity(request);
        Set<Category> categories = categoryService.getCategoriesByIds(request.categoryIds());
        product.setCategories(categories);
        return product;
    }
}
