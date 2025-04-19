package com.example.CloudBalanceBackend.service.Implimentation;

import com.example.CloudBalanceBackend.dto.AccountDTO;
import com.example.CloudBalanceBackend.model.Account;
import com.example.CloudBalanceBackend.model.User;
import com.example.CloudBalanceBackend.repository.AccountRepository;
import com.example.CloudBalanceBackend.repository.UserRepository;
import com.example.CloudBalanceBackend.service.AccountService;
import com.example.CloudBalanceBackend.util.AccountDtoEntityMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountDtoEntityMapping accountDtoEntityMapping;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, AccountDtoEntityMapping accountDtoEntityMapping, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.accountDtoEntityMapping = accountDtoEntityMapping;
        this.userRepository = userRepository;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean checkRole = authentication
                .getAuthorities()
                .stream()
                .anyMatch(auth -> auth
                        .getAuthority()
                        .equals("ROLE_CUSTOMER"));

        if (checkRole) {
            String email = authentication.getName();
            User entity = userRepository
                    .findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Error finding User"));
            List<Account> accounts= entity.getAccountList();
            return accounts.stream()
                    .map(accountDtoEntityMapping::map)
                    .collect(Collectors.toList());
        } else {
            List<Account> entities = accountRepository.findAll();
            return entities.stream()
                    .map(accountDtoEntityMapping::map)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public AccountDTO getAccountById(Long id) {
        Account user = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account With id: " + id + " not found"));
        return accountDtoEntityMapping.map(user);
    }

    @Override
    public AccountDTO updateAccount(Long id, AccountDTO accountDTO) {
        Account user = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account With id: " + id + " not found"));
        accountDtoEntityMapping.updateEntityFromDto(accountDTO, user);
        return accountDtoEntityMapping.map(user);
    }
}
