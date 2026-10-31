package com.enterprise.user.repository;

import com.enterprise.user.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByCustomerId(Long customerId);
    Optional<UserProfile> findByEmail(String email);
}