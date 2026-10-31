package com.enterprise.shipping.service;

import com.enterprise.common.event.PaymentCompletedEvent;
import com.enterprise.shipping.entity.Shipment;
import com.enterprise.shipping.repository.ShippingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ShippingService {

    private static final Logger log = LoggerFactory.getLogger(ShippingService.class);
    private final ShippingRepository shippingRepository;

    public ShippingService(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }

    @Transactional
    public void processShipment(PaymentCompletedEvent event) {
        log.info("Processing shipment fulfillment for paid Order ID: {}", event.orderId());

        String trackingNum = "TRK-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();

        Shipment shipment = Shipment.builder()
                .orderId(event.orderId())
                .customerId(event.customerId())
                .trackingNumber(trackingNum)
                .status("PROCESSING")
                .build();

        shippingRepository.save(shipment);
        log.info("Created shipment record with Tracking Number: {} for Order ID: {}", trackingNum, event.orderId());
    }

    public Shipment getShipmentByOrderId(String orderId) {
        return shippingRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Shipment not found for order ID: " + orderId));
    }
}