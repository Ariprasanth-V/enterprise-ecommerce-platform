package com.enterprise.common.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentCompletedEvent(
        String paymentId,
        String orderId,
        Long customerId,
        BigDecimal amount,
        String razorpayPaymentId,
        String razorpayOrderStatus,
        LocalDateTime completedAt
) {}