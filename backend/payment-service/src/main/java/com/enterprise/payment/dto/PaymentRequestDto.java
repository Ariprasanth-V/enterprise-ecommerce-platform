package com.enterprise.payment.dto;

import java.math.BigDecimal;

public record PaymentRequestDto(
        String orderId,
        Long customerId,
        BigDecimal amount
) {}