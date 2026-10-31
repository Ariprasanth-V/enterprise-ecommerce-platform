package com.enterprise.notification.service;

import com.enterprise.common.event.OrderCreatedEvent;
import com.enterprise.common.event.PaymentCompletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public void sendOrderConfirmationEmail(OrderCreatedEvent event) {
        log.info("==================================================");
        log.info(" [EMAIL NOTIFICATION] Sending Order Confirmation");
        log.info(" Customer ID : {}", event.customerId());
        log.info(" Order ID    : {}", event.orderId());
        log.info(" Total Amount: INR {}", event.totalAmount());
        log.info(" Status      : PENDING_PAYMENT");
        log.info("==================================================");
    }

    public void sendPaymentSuccessEmail(PaymentCompletedEvent event) {
        log.info("==================================================");
        log.info(" [SMS/EMAIL NOTIFICATION] Payment Successful");
        log.info(" Customer ID : {}", event.customerId());
        log.info(" Order ID    : {}", event.orderId());
        log.info(" Payment ID  : {}", event.razorpayPaymentId());
        log.info(" Amount Paid : INR {}", event.amount());
        log.info("==================================================");
    }
}