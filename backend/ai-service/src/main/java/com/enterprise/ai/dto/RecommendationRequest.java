package com.enterprise.ai.dto;

public record RecommendationRequest(
        Long customerId,
        String searchContext
) {}