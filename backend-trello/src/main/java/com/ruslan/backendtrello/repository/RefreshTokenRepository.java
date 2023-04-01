package com.ruslan.backendtrello.repository;

import com.ruslan.backendtrello.models.sql.RefreshToken;
import com.ruslan.backendtrello.models.sql.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    int deleteByUser(User user);
}
