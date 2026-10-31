package com.enterprise.product.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String sku,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        String category
) {}