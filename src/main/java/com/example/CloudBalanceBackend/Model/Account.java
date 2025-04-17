package com.example.CloudBalanceBackend.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "account_data")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false, name = "account_id")
    private Long accountId;

    @Column(unique = true, nullable = false)
    private String arn;
}
