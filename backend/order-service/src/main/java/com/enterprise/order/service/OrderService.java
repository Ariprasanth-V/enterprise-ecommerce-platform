package com.enterprise.order.service;

import com.enterprise.common.event.OrderCreatedEvent;
import com.enterprise.order.dto.OrderRequestDto;
import com.enterprise.order.dto.OrderResponseDto;
import com.enterprise.order.entity.Order;
import com.enterprise.order.entity.OrderItem;
import com.enterprise.order.kafka.OrderProducer;
import com.enterprise.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public OrderService(OrderRepository orderRepository, OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        String generatedOrderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Order order = Order.builder()
                .orderId(generatedOrderId)
                .customerId(requestDto.customerId())
                .totalAmount(requestDto.totalAmount())
                .status("PENDING")
                .build();

        var orderItems = requestDto.items().stream().map(itemDto ->
                OrderItem.builder()
                        .order(order)
                        .productId(itemDto.productId())
                        .sku(itemDto.sku())
                        .quantity(itemDto.quantity())
                        .price(itemDto.price())
                        .build()
        ).collect(Collectors.toList());

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        // Publish OrderCreatedEvent to Kafka
        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getOrderId(),
                savedOrder.getCustomerId(),
                savedOrder.getTotalAmount(),
                savedOrder.getItems().stream().map(i -> new OrderCreatedEvent.OrderItemDto(
                        i.getProductId(), i.getSku(), i.getQuantity(), i.getPrice()
                )).collect(Collectors.toList()),
                savedOrder.getCreatedAt()
        );

        orderProducer.sendOrderCreatedEvent(event);

        return mapToDto(savedOrder);
    }

    public OrderResponseDto getOrderByOrderId(String orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        return mapToDto(order);
    }

    private OrderResponseDto mapToDto(Order order) {
        return new OrderResponseDto(
                order.getOrderId(),
                order.getCustomerId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream().map(i -> new OrderResponseDto.OrderItemResponseDto(
                        i.getProductId(), i.getSku(), i.getQuantity(), i.getPrice()
                )).collect(Collectors.toList()),
                order.getCreatedAt()
        );
    }
}