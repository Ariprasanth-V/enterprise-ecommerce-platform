package com.enterprise.inventory.controller;

import com.enterprise.inventory.dto.InventoryRequestDto;
import com.enterprise.inventory.entity.Inventory;
import com.enterprise.inventory.repository.InventoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product ID: " + productId));
        return ResponseEntity.ok(inventory);
    }

    @PostMapping
    public ResponseEntity<Inventory> addOrUpdateInventory(@RequestBody InventoryRequestDto requestDto) {
        Inventory inventory = inventoryRepository.findByProductId(requestDto.productId())
                .orElse(Inventory.builder()
                        .productId(requestDto.productId())
                        .sku(requestDto.sku())
                        .availableQuantity(0)
                        .build());

        inventory.setAvailableQuantity(inventory.getAvailableQuantity() + requestDto.availableQuantity());
        Inventory saved = inventoryRepository.save(inventory);
        return ResponseEntity.ok(saved);
    }
}