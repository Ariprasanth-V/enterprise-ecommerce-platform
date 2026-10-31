package com.enterprise.review.service;

import com.enterprise.review.dto.ReviewRequestDto;
import com.enterprise.review.dto.ReviewResponseDto;
import com.enterprise.review.entity.Review;
import com.enterprise.review.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private static final Logger log = LoggerFactory.getLogger(ReviewService.class);
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto requestDto) {
        if (requestDto.rating() < 1 || requestDto.rating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5 stars");
        }

        Review review = Review.builder()
                .productId(requestDto.productId())
                .customerId(requestDto.customerId())
                .rating(requestDto.rating())
                .comment(requestDto.comment())
                .build();

        Review saved = reviewRepository.save(review);
        log.info("Saved review ID {} for Product ID {} by Customer ID {}", saved.getId(), saved.getProductId(), saved.getCustomerId());

        return mapToDto(saved);
    }

    public List<ReviewResponseDto> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductId(productId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ReviewResponseDto mapToDto(Review review) {
        return new ReviewResponseDto(
                review.getId(),
                review.getProductId(),
                review.getCustomerId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }
}