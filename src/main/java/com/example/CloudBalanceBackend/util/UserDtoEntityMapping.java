package com.example.CloudBalanceBackend.util;

import com.example.CloudBalanceBackend.DTO.UserDTO;
import com.example.CloudBalanceBackend.Model.Account;
import com.example.CloudBalanceBackend.Model.Erole;
import com.example.CloudBalanceBackend.Model.User;
import com.example.CloudBalanceBackend.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserDtoEntityMapping {

    private final PasswordEncoder encoder;
    private final AccountRepository accountRepository;

    @Autowired
    public UserDtoEntityMapping(PasswordEncoder encoder, AccountRepository accountRepository) {
        this.encoder = encoder;
        this.accountRepository = accountRepository;
    }

    public UserDTO map(User entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole().name());
        dto.setLastLogin(entity.getLastLogin());
        dto.setAccountIds(entity.getAccountList()
                .stream()
                .map(Account::getId)
                .collect(Collectors.toList())
        );

        return dto;
    }

    public User map(UserDTO dto) {
        User entity = new User();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(encoder.encode(dto.getPassword()));
        entity.setLastLogin(dto.getLastLogin());
        Erole role = Erole.valueOf(dto.getRole());
        entity.setRole(role);
        if (role == Erole.ROLE_CUSTOMER && dto.getAccountIds() != null) {
            List<Account> accounts = dto
                    .getAccountIds()
                    .stream()
                    .map(id -> accountRepository
                            .findById(id)
                            .orElseThrow(() -> new RuntimeException("Account ID " + id + " not found.")))
                    .collect(Collectors.toList());
            entity.setAccountList(accounts);
        } else {
            entity.setAccountList(new ArrayList<>());
        }
        return entity;
    }

    public void updateEntityFromDto(UserDTO dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        if(!(dto.getPassword() == null) && !dto.getPassword().isBlank()){
            entity.setPassword(encoder.encode(dto.getPassword()));
        }

        entity.setLastLogin(dto.getLastLogin());
        Erole role = Erole.valueOf(dto.getRole());
        entity.setRole(role);
        if (role == Erole.ROLE_CUSTOMER && dto.getAccountIds() != null) {
            List<Account> accounts = dto
                    .getAccountIds()
                    .stream()
                    .map(id -> accountRepository
                            .findById(id)
                            .orElseThrow(() -> new RuntimeException("Account ID " + id + " not found.")))
                    .collect(Collectors.toList());
            entity.setAccountList(accounts);
        } else {
            entity.setAccountList(new ArrayList<>());
        }
    }
}
