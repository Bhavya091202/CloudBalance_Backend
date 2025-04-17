package com.example.CloudBalanceBackend.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "User_Detail")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Email
    @NotNull
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private Erole role;

    @Column(name = "first_name", nullable = false)
    @NotNull
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotNull
    private String lastName;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @ManyToMany
    @JoinTable(
         name = "user_account_mapping",
         joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
         inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id")
    )
    private List<Account> accountList;
}
