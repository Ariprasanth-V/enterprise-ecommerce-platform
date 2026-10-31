package com.enterprise.ai.service;

import com.enterprise.ai.dto.RecommendationRequest;
import com.enterprise.ai.dto.RecommendationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiRecommendationService {

    private static final Logger log = LoggerFactory.getLogger(AiRecommendationService.class);

    public RecommendationResponse getRecommendations(RecommendationRequest request) {
        log.info("Generating AI recommendations for customer ID: {} based on context: {}",
                request.customerId(), request.searchContext());

        // Simulated AI inference output or integration point for LLM / Vector DB embeddings
        List<String> suggestedSkus = List.of("SKU-PROD-001", "SKU-PROD-004", "SKU-PROD-009");

        String message = "Based on your recent browsing history and preferences, here are top picks tailored for you!";

        return new RecommendationResponse(message, suggestedSkus);
    }
}