package com.enterprise.common.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderCreatedEvent(
        String orderId,
        Long customerId,
        BigDecimal totalAmount,
        List<OrderItemDto> items,
        LocalDateTime createdAt
) {
    public record OrderItemDto(
            Long productId,
            String sku,
            Integer quantity,
            BigDecimal price
    ) {}
}