package com.enterprise.shipping.controller;

import com.enterprise.shipping.entity.Shipment;
import com.enterprise.shipping.service.ShippingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipping")
public class ShippingController {

    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Shipment> getShipmentStatus(@PathVariable String orderId) {
        return ResponseEntity.ok(shippingService.getShipmentByOrderId(orderId));
    }
}