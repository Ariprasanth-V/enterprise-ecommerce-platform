package com.enterprise.inventory.dto;

public record InventoryRequestDto(
        Long productId,
        String sku,
        Integer availableQuantity
) {}