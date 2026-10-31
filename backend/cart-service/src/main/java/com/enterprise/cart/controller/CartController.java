package com.enterprise.cart.controller;

import com.enterprise.cart.dto.CartDto;
import com.enterprise.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String customerId) {
        return ResponseEntity.ok(cartService.getCartByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<CartDto> saveCart(@RequestBody CartDto cartDto) {
        return ResponseEntity.ok(cartService.saveOrUpdateCart(cartDto));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> clearCart(@PathVariable String customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.ok().build();
    }
}