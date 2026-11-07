package com.enterprise.ai.service;

import com.enterprise.ai.dto.RecommendationRequest;
import com.enterprise.ai.dto.RecommendationResponse;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiRecommendationService {

    private static final Logger log = LoggerFactory.getLogger(AiRecommendationService.class);
    private final Client client;

    public AiRecommendationService(@Value("${gemini.api.key}") String apiKey) {
        this.client = new Client.Builder().apiKey(apiKey).build();
    }

    public RecommendationResponse getRecommendations(RecommendationRequest request) {
        log.info("Generating AI recommendations for customer ID: {} based on context: {}",
                request.customerId(), request.searchContext());

        try {
            String prompt = String.format(
                    "You are an expert e-commerce recommendation engine. A user (ID: %d) is searching with context: \"%s\". " +
                            "Provide a friendly shopping response message and suggest relevant product SKUs.",
                    request.customerId(), request.searchContext()
            );

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash",
                    prompt,
                    null
            );

            String aiMessage = response.text();
            List<String> suggestedSkus = List.of("SKU-PROD-001", "SKU-PROD-004"); // Can be parsed dynamically from response

            return new RecommendationResponse(aiMessage, suggestedSkus);

        } catch (Exception e) {
            log.error("Failed to generate Gemini AI recommendations", e);
            return new RecommendationResponse(
                    "Hello! I am having trouble connecting to my AI core right now, but feel free to browse our catalog.",
                    List.of()
            );
        }
    }
}