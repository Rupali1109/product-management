package com.nowvertical.management.product.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private String name;
    private String description;
    private BigDecimal price;

}
