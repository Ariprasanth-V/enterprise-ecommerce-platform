package com.enterprise.payment.controller;

import com.enterprise.payment.dto.PaymentVerificationDto;
import com.enterprise.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody PaymentVerificationDto verificationDto) {
        boolean verified = paymentService.verifyAndCompletePayment(verificationDto);
        if (verified) {
            return ResponseEntity.ok(Map.of("status", "SUCCESS", "message", "Payment verified successfully"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("status", "FAILED", "message", "Invalid signature"));
        }
    }
}