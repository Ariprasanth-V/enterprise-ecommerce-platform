package com.enterprise.cart.service;

import com.enterprise.cart.dto.CartDto;
import com.enterprise.cart.model.Cart;
import com.enterprise.cart.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartDto getCartByCustomerId(String customerId) {
        Cart cart = cartRepository.findById(customerId)
                .orElse(Cart.builder()
                        .customerId(customerId)
                        .items(Collections.emptyList())
                        .totalPrice(BigDecimal.ZERO)
                        .build());
        return mapToDto(cart);
    }

    public CartDto saveOrUpdateCart(CartDto cartDto) {
        BigDecimal total = cartDto.items().stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Cart cart = Cart.builder()
                .customerId(cartDto.customerId())
                .items(cartDto.items().stream().map(i -> new Cart.CartItem(
                        i.productId(), i.sku(), i.name(), i.quantity(), i.price()
                )).collect(Collectors.toList()))
                .totalPrice(total)
                .build();

        Cart saved = cartRepository.save(cart);
        return mapToDto(saved);
    }

    public void clearCart(String customerId) {
        cartRepository.deleteById(customerId);
    }

    private CartDto mapToDto(Cart cart) {
        return new CartDto(
                cart.getCustomerId(),
                cart.getItems() == null ? Collections.emptyList() : cart.getItems().stream().map(i -> new CartDto.CartItemDto(
                        i.getProductId(), i.getSku(), i.getName(), i.getQuantity(), i.getPrice()
                )).collect(Collectors.toList()),
                cart.getTotalPrice()
        );
    }
}