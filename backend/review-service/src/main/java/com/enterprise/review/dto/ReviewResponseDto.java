package com.enterprise.review.dto;

import java.time.LocalDateTime;

public record ReviewResponseDto(
        Long id,
        Long productId,
        Long customerId,
        Integer rating,
        String comment,
        LocalDateTime createdAt
) {}