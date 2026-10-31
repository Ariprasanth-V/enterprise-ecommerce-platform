package com.enterprise.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        String orderId,
        Long customerId,
        BigDecimal totalAmount,
        String status,
        List<OrderItemResponseDto> items,
        LocalDateTime createdAt
) {
    public record OrderItemResponseDto(
            Long productId,
            String sku,
            Integer quantity,
            BigDecimal price
    ) {}
}