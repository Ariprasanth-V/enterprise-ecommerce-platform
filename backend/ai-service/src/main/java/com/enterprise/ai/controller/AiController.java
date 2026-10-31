package com.enterprise.ai.controller;

import com.enterprise.ai.dto.RecommendationRequest;
import com.enterprise.ai.dto.RecommendationResponse;
import com.enterprise.ai.service.AiRecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    private final AiRecommendationService aiRecommendationService;

    public AiController(AiRecommendationService aiRecommendationService) {
        this.aiRecommendationService = aiRecommendationService;
    }

    @PostMapping("/recommendations")
    public ResponseEntity<RecommendationResponse> getRecommendations(@RequestBody RecommendationRequest request) {
        return ResponseEntity.ok(aiRecommendationService.getRecommendations(request));
    }
}