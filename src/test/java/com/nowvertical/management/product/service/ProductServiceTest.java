package com.nowvertical.management.product.service;

import com.nowvertical.management.product.dto.ProductDto;
import com.nowvertical.management.product.entity.Product;
import com.nowvertical.management.product.exception.ProductNotFoundException;
import com.nowvertical.management.product.repository.ProductRepository;
import com.nowvertical.management.product.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = Arrays.asList(
                Product.builder().id(1L).name("Product1").build(),
                Product.builder().id(2L).name("Product2").build()
        );
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Success() {
        Product product = Product.builder().id(1L).name("Product1").build();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Product1", result.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateProduct() {
        ProductDto dto = new ProductDto();
        dto.setName("New Product");
        dto.setDescription("New Description");
        dto.setPrice(new BigDecimal(100));

        Product savedProduct = Product.builder()
                .id(1L)
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product result = productService.createProduct(dto);

        assertNotNull(result);
        assertEquals("New Product", result.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_Success() {
        Long productId = 1L;
        ProductDto dto = new ProductDto();
        dto.setName("Updated Product");
        dto.setDescription("Updated Description");
        dto.setPrice(new BigDecimal(200));

        Product existing = Product.builder()
                .id(productId)
                .name("Old Product")
                .description("Old Description")
                .price(new BigDecimal(100))
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now().minusDays(1))
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenReturn(existing);

        Product result = productService.updateProduct(productId, dto);

        assertEquals("Updated Product", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(new BigDecimal(200), result.getPrice());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existing);
    }

    @Test
    void testUpdateProduct_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductDto dto = new ProductDto();
        dto.setName("Name");
        dto.setDescription("Description");
        dto.setPrice(new BigDecimal(10));

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1L, dto));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteProduct_Success() {
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);
        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productRepository.existsById(1L)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1L));
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(0)).deleteById(1L);
    }
}
