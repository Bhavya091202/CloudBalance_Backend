package com.example.CloudBalanceBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "cost_explorer_group_filter")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CostExplorerGroupFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String databaseName;
    private String displayName;
}


