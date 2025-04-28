package com.nowvertical.management.product.service;

import com.nowvertical.management.product.dto.ProductDto;
import com.nowvertical.management.product.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product createProduct(ProductDto dto);

    Product updateProduct(Long id, ProductDto dto);

    void deleteProduct(Long id);
}