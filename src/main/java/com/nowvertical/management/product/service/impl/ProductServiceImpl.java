package com.nowvertical.management.product.service.impl;

import com.nowvertical.management.product.dto.ProductDto;
import com.nowvertical.management.product.entity.Product;
import com.nowvertical.management.product.exception.ProductNotFoundException;
import com.nowvertical.management.product.repository.ProductRepository;
import com.nowvertical.management.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }

    @Override
    public Product createProduct(ProductDto dto) {
        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, ProductDto dto) {
        Product existingProduct = getProductById(id);

        if (dto.getName() != null) {
            existingProduct.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            existingProduct.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            existingProduct.setPrice(dto.getPrice());
        }
        existingProduct.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }
}
