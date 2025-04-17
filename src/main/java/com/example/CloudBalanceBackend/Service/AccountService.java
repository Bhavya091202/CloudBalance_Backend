package com.example.CloudBalanceBackend.Service;

import com.example.CloudBalanceBackend.DTO.AccountDTO;
import com.example.CloudBalanceBackend.DTO.UserDTO;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);
    List<AccountDTO> getAllAccounts();
    AccountDTO getAccountById(Long id);
    AccountDTO updateAccount(Long id, AccountDTO accountDTO);
}
