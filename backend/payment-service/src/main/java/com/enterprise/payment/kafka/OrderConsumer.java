package com.enterprise.payment.kafka;

import com.enterprise.common.event.OrderCreatedEvent;
import com.enterprise.payment.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private final PaymentService paymentService;

    public OrderConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "order-created-topic", groupId = "payment-service-group")
    public void consumeOrderCreatedEvent(OrderCreatedEvent event) {
        paymentService.createRazorpayOrderForEvent(event);
    }
}