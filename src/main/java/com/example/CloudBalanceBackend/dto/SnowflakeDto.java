package com.example.CloudBalanceBackend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class SnowflakeDto {
    private String groupBy;
    private Map<String, List<String>> filters;
    private LocalDate startDate;
    private LocalDate endDate;

}

