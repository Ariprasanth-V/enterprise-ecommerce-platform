package com.enterprise.inventory.service;

import com.enterprise.common.event.OrderCreatedEvent;
import com.enterprise.inventory.entity.Inventory;
import com.enterprise.inventory.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public void reserveStock(OrderCreatedEvent event) {
        log.info("Processing inventory reservation for Order ID: {}", event.orderId());

        for (OrderCreatedEvent.OrderItemDto item : event.items()) {
            Inventory inventory = inventoryRepository.findByProductId(item.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found in inventory: " + item.productId()));

            if (inventory.getAvailableQuantity() < item.quantity()) {
                throw new RuntimeException("Insufficient stock for product ID: " + item.productId());
            }

            inventory.setAvailableQuantity(inventory.getAvailableQuantity() - item.quantity());
            inventoryRepository.save(inventory);

            log.info("Successfully reserved {} units for Product SKU: {}", item.quantity(), item.sku());
        }
    }
}