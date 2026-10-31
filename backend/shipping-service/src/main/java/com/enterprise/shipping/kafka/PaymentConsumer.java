package com.enterprise.shipping.kafka;

import com.enterprise.common.event.PaymentCompletedEvent;
import com.enterprise.shipping.service.ShippingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {

    private final ShippingService shippingService;

    public PaymentConsumer(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @KafkaListener(topics = "payment-completed-topic", groupId = "shipping-service-group")
    public void consumePaymentCompleted(PaymentCompletedEvent event) {
        shippingService.processShipment(event);
    }
}