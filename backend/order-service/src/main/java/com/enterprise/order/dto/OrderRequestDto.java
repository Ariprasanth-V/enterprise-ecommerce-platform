package com.enterprise.order.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequestDto(
        Long customerId,
        BigDecimal totalAmount,
        List<OrderItemRequestDto> items
) {
    public record OrderItemRequestDto(
            Long productId,
            String sku,
            Integer quantity,
            BigDecimal price
    ) {}
}