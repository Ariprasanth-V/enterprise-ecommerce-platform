package com.enterprise.cart.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@RedisHash("Cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    private String customerId;
    private List<CartItem> items;
    private BigDecimal totalPrice;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CartItem {
        private Long productId;
        private String sku;
        private String name;
        private Integer quantity;
        private BigDecimal price;
    }
}