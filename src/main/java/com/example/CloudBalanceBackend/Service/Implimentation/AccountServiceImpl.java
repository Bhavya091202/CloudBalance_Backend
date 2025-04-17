package com.example.CloudBalanceBackend.Service.Implimentation;

import com.example.CloudBalanceBackend.DTO.AccountDTO;
import com.example.CloudBalanceBackend.DTO.UserDTO;
import com.example.CloudBalanceBackend.Model.Account;
import com.example.CloudBalanceBackend.Model.User;
import com.example.CloudBalanceBackend.Repository.AccountRepository;
import com.example.CloudBalanceBackend.Service.AccountService;
import com.example.CloudBalanceBackend.util.AccountDtoEntityMapping;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private  final AccountDtoEntityMapping accountDtoEntityMapping;

    public AccountServiceImpl(AccountRepository accountRepository, AccountDtoEntityMapping accountDtoEntityMapping) {
        this.accountRepository = accountRepository;
        this.accountDtoEntityMapping = accountDtoEntityMapping;
    }


    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account entity = accountDtoEntityMapping.map(accountDTO);
        accountRepository.save(entity);
        accountDTO.setId(entity.getId());
        return accountDTO;
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        List<Account> entities = accountRepository.findAll();
        return entities.stream()
                .map(accountDtoEntityMapping::map)
                .collect(Collectors.toList());    }

    @Override
    public AccountDTO getAccountById(Long id) {
        Account user = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account With id: " + id + " not found"));
        return accountDtoEntityMapping.map(user);
    }

    @Override
    public AccountDTO updateAccount(Long id, AccountDTO accountDTO) {
        Account user = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account With id: " + id + " not found"));
        accountDtoEntityMapping.updateEntityFromDto(accountDTO, user);
        return accountDtoEntityMapping.map(user);    }
}
