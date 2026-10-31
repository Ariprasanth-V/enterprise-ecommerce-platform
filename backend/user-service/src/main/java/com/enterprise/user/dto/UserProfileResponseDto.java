package com.enterprise.user.dto;

import java.time.LocalDateTime;

public record UserProfileResponseDto(
        Long customerId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String shippingAddress,
        LocalDateTime updatedAt
) {}