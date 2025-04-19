package com.example.CloudBalanceBackend.util;

import com.example.CloudBalanceBackend.dto.AccountDTO;
import com.example.CloudBalanceBackend.model.Account;
import com.example.CloudBalanceBackend.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class AccountDtoEntityMapping {

    private final UserRepository userRepository;

    public AccountDtoEntityMapping(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AccountDTO map(Account entity) {
        AccountDTO dto = new AccountDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setArn(entity.getArn());
        dto.setAccountId(entity.getAccountId());
//        dto.setUserEmail(entity.getUser().getEmail());
        return dto;
    }

    public Account map(AccountDTO dto) {
        Account entity = new Account();
        entity.setId(dto.getId());
        entity.setAccountId(dto.getAccountId());
        entity.setArn(dto.getArn());
        entity.setName(dto.getName());
//        if (dto.getUserEmail() != null) {
//            User user = userRepository.findByEmail(dto.getUserEmail())
//                    .orElseThrow(() -> new RuntimeException("User not found: " + dto.getUserEmail()));
//            entity.setUser(user);
//        }

        return entity;
    }

    public void updateEntityFromDto(AccountDTO dto, Account entity) {
        entity.setId(dto.getId());
        entity.setAccountId(dto.getAccountId());
        entity.setArn(dto.getArn());
        entity.setName(dto.getName());

//        if (dto.getUserEmail() != null) {
//            User user = userRepository.findByEmail(dto.getUserEmail())
//                    .orElseThrow(() -> new RuntimeException("User not found: " + dto.getUserEmail()));
//            entity.setUser(user);
//        }
    }
}
