package com.enterprise.ai.dto;

import java.util.List;

public record RecommendationResponse(
        String aiMessage,
        List<String> recommendedProductSkus
) {}