package com.example.CloudBalanceBackend.Repository;

import com.example.CloudBalanceBackend.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
