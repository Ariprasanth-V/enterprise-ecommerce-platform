package com.enterprise.payment.dto;

public record PaymentVerificationDto(
        String orderId,
        String razorpayOrderId,
        String razorpayPaymentId,
        String razorpaySignature
) {}