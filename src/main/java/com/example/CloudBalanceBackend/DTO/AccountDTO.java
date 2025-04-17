package com.example.CloudBalanceBackend.DTO;

import lombok.Data;

@Data
public class AccountDTO {
    private Long id;
    private Long accountId;
    private String name;
    private String arn;
    private String userEmail;
}
