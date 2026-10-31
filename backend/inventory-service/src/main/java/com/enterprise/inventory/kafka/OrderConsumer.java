package com.enterprise.inventory.kafka;

import com.enterprise.common.event.OrderCreatedEvent;
import com.enterprise.inventory.service.InventoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private final InventoryService inventoryService;

    public OrderConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "order-created-topic", groupId = "inventory-service-group")
    public void consumeOrderCreatedEvent(OrderCreatedEvent event) {
        inventoryService.reserveStock(event);
    }
}