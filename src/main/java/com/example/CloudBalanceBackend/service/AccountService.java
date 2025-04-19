package com.example.CloudBalanceBackend.service;

import com.example.CloudBalanceBackend.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);
    List<AccountDTO> getAllAccounts();
    AccountDTO getAccountById(Long id);
    AccountDTO updateAccount(Long id, AccountDTO accountDTO);
}
