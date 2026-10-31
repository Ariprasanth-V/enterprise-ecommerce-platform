package com.enterprise.notification.kafka;

import com.enterprise.common.event.OrderCreatedEvent;
import com.enterprise.common.event.PaymentCompletedEvent;
import com.enterprise.notification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "order-created-topic", groupId = "notification-service-group")
    public void consumeOrderCreated(OrderCreatedEvent event) {
        notificationService.sendOrderConfirmationEmail(event);
    }

    @KafkaListener(topics = "payment-completed-topic", groupId = "notification-service-group")
    public void consumePaymentCompleted(PaymentCompletedEvent event) {
        notificationService.sendPaymentSuccessEmail(event);
    }
}