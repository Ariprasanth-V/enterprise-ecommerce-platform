package com.enterprise.payment.service;

import com.enterprise.common.event.OrderCreatedEvent;
import com.enterprise.common.event.PaymentCompletedEvent;
import com.enterprise.payment.dto.PaymentVerificationDto;
import com.enterprise.payment.entity.Payment;
import com.enterprise.payment.kafka.PaymentProducer;
import com.enterprise.payment.repository.PaymentRepository;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository paymentRepository;
    private final PaymentProducer paymentProducer;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    public PaymentService(PaymentRepository paymentRepository, PaymentProducer paymentProducer) {
        this.paymentRepository = paymentRepository;
        this.paymentProducer = paymentProducer;
    }

    @Transactional
    public void createRazorpayOrderForEvent(OrderCreatedEvent event) {
        try {
            RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

            JSONObject orderRequest = new JSONObject();
            // Razorpay amount is in paise (multiply by 100)
            orderRequest.put("amount", event.totalAmount().multiply(BigDecimal.valueOf(100)).intValue());
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", event.orderId());

            com.razorpay.Order razorpayOrder = razorpay.orders.create(orderRequest);
            String rzpOrderId = razorpayOrder.get("id");

            Payment payment = Payment.builder()
                    .orderId(event.orderId())
                    .customerId(event.customerId())
                    .amount(event.totalAmount())
                    .razorpayOrderId(rzpOrderId)
                    .status("CREATED")
                    .build();

            paymentRepository.save(payment);
            log.info("Created Razorpay order {} for Platform Order ID: {}", rzpOrderId, event.orderId());

        } catch (Exception e) {
            log.error("Failed to create Razorpay order for order ID: {}", event.orderId(), e);
            throw new RuntimeException("Payment gateway initialization failed", e);
        }
    }

    @Transactional
    public boolean verifyAndCompletePayment(PaymentVerificationDto verificationDto) {
        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", verificationDto.razorpayOrderId());
            options.put("razorpay_payment_id", verificationDto.razorpayPaymentId());
            options.put("razorpay_signature", verificationDto.razorpaySignature());

            boolean isSignatureValid = Utils.verifyPaymentSignature(options, razorpayKeySecret);

            if (isSignatureValid) {
                Payment payment = paymentRepository.findByOrderId(verificationDto.orderId())
                        .orElseThrow(() -> new RuntimeException("Payment record not found for order ID: " + verificationDto.orderId()));

                payment.setRazorpayPaymentId(verificationDto.razorpayPaymentId());
                payment.setRazorpaySignature(verificationDto.razorpaySignature());
                payment.setStatus("PAID");
                paymentRepository.save(payment);

                // Publish PaymentCompletedEvent
                PaymentCompletedEvent event = new PaymentCompletedEvent(
                        payment.getId().toString(),
                        payment.getOrderId(),
                        payment.getCustomerId(),
                        payment.getAmount(),
                        payment.getRazorpayPaymentId(),
                        payment.getStatus(),
                        LocalDateTime.now()
                );
                paymentProducer.sendPaymentCompletedEvent(event);

                log.info("Payment verified and completed successfully for Order ID: {}", payment.getOrderId());
                return true;
            } else {
                log.warn("Invalid payment signature for Order ID: {}", verificationDto.orderId());
                return false;
            }
        } catch (Exception e) {
            log.error("Error verifying payment signature for Order ID: {}", verificationDto.orderId(), e);
            throw new RuntimeException("Payment verification failed", e);
        }
    }
}