package com.nowvertical.management.product.controller;

import com.nowvertical.management.product.dto.ProductDto;
import com.nowvertical.management.product.entity.Product;
import com.nowvertical.management.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Controller", description = "Operations related to managing products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(
            summary = "Get All Products",
            description = "Returns details of all products available in the system."
    )
    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Product By ID",
            description = "Returns the details of a specific product identified by its ID."
    )
    public Product getProductById(@PathVariable Long id) {
        logger.info("Fetching product with ID: {}", id);
        return productService.getProductById(id);
    }

    @PostMapping
    @Operation(
            summary = "Create a New Product",
            description = "Creates a new product with the provided details."
    )
    public Product createProduct(@RequestBody ProductDto dto) {
        return productService.createProduct(dto);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update Existing Product",
            description = "Updates the details of an existing product identified by its ID."
    )
    public Product updateProduct(@PathVariable Long id, @RequestBody ProductDto dto) {
        logger.info("Updating product ID: {}", id);
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Product",
            description = "Deletes a specific product identified by its ID."
    )
    public String deleteProduct(@PathVariable Long id) {
        logger.info("Deleting product ID: {}", id);
        productService.deleteProduct(id);
        return "Delete successfully";
    }
}