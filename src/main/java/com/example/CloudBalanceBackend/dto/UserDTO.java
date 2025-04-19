package com.example.CloudBalanceBackend.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
    private LocalDateTime lastLogin;
    private List<Long> accountIds;
}
