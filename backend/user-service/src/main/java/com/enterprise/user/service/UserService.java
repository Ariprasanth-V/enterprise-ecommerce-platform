package com.enterprise.user.service;

import com.enterprise.user.dto.UserProfileRequestDto;
import com.enterprise.user.dto.UserProfileResponseDto;
import com.enterprise.user.entity.UserProfile;
import com.enterprise.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserProfileResponseDto saveOrUpdateProfile(UserProfileRequestDto requestDto) {
        UserProfile profile = userRepository.findByCustomerId(requestDto.customerId())
                .orElse(UserProfile.builder().customerId(requestDto.customerId()).build());

        profile.setFirstName(requestDto.firstName());
        profile.setLastName(requestDto.lastName());
        profile.setEmail(requestDto.email());
        profile.setPhoneNumber(requestDto.phoneNumber());
        profile.setShippingAddress(requestDto.shippingAddress());

        UserProfile saved = userRepository.save(profile);
        log.info("Saved user profile for customer ID: {}", saved.getCustomerId());

        return mapToDto(saved);
    }

    public UserProfileResponseDto getProfileByCustomerId(Long customerId) {
        UserProfile profile = userRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("User profile not found for customer ID: " + customerId));
        return mapToDto(profile);
    }

    private UserProfileResponseDto mapToDto(UserProfile profile) {
        return new UserProfileResponseDto(
                profile.getCustomerId(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getEmail(),
                profile.getPhoneNumber(),
                profile.getShippingAddress(),
                profile.getUpdatedAt()
        );
    }
}