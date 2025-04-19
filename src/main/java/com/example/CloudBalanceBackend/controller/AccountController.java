package com.example.CloudBalanceBackend.controller;

import com.example.CloudBalanceBackend.dto.AccountDTO;
import com.example.CloudBalanceBackend.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccountDTO> createUser(@Valid @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.createAccount(accountDTO));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_READ_ONLY', 'ROLE_CUSTOMER')")
    public ResponseEntity<List<AccountDTO>> getUsersAll() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccountDTO> updateUser(@PathVariable Long id, @Valid @RequestBody AccountDTO dto){
        return ResponseEntity.ok(accountService.updateAccount(id, dto));
    }
}
