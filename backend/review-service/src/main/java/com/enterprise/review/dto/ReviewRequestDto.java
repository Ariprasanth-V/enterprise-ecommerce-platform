package com.enterprise.review.dto;

public record ReviewRequestDto(
        Long productId,
        Long customerId,
        Integer rating,
        String comment
) {}