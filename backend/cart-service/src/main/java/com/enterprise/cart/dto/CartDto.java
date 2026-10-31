package com.enterprise.cart.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartDto(
        String customerId,
        List<CartItemDto> items,
        BigDecimal totalPrice
) {
    public record CartItemDto(
            Long productId,
            String sku,
            String name,
            Integer quantity,
            BigDecimal price
    ) {}
}