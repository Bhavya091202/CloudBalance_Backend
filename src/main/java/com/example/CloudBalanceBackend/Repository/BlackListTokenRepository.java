package com.example.CloudBalanceBackend.Repository;

import com.example.CloudBalanceBackend.Model.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListTokenRepository extends JpaRepository<BlackListToken, Long> {
    boolean existsByToken(String token);
}
