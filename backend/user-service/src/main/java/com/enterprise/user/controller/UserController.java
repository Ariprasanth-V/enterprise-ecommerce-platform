package com.enterprise.user.controller;

import com.enterprise.user.dto.UserProfileRequestDto;
import com.enterprise.user.dto.UserProfileResponseDto;
import com.enterprise.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<UserProfileResponseDto> getUserProfile(@PathVariable Long customerId) {
        return ResponseEntity.ok(userService.getProfileByCustomerId(customerId));
    }

    @PutMapping
    public ResponseEntity<UserProfileResponseDto> updateProfile(@RequestBody UserProfileRequestDto requestDto) {
        return ResponseEntity.ok(userService.saveOrUpdateProfile(requestDto));
    }
}