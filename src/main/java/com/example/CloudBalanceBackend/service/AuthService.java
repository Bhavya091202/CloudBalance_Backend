package com.example.CloudBalanceBackend.service;

import com.example.CloudBalanceBackend.dto.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> authenticate(LoginDTO loginDTO);
    ResponseEntity<?> logout(HttpServletRequest request);
}
