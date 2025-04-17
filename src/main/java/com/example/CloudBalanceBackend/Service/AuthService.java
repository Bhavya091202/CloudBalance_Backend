package com.example.CloudBalanceBackend.Service;

import com.example.CloudBalanceBackend.DTO.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> authenticate(LoginDTO loginDTO);
    ResponseEntity<?> logout(HttpServletRequest request);
}
