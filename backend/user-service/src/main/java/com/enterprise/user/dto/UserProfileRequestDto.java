package com.enterprise.user.dto;

public record UserProfileRequestDto(
        Long customerId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String shippingAddress
) {}