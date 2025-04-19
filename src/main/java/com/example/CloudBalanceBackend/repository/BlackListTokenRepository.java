package com.example.CloudBalanceBackend.repository;

import com.example.CloudBalanceBackend.model.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListTokenRepository extends JpaRepository<BlackListToken, Long> {
    boolean existsByToken(String token);
}
