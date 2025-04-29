package com.example.CloudBalanceBackend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Data
public class SnowflakeDto {
    private String groupBy;
    private Map<String, List<String>> filters;
    private String startDate;
    private String endDate;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-yyyy");

    public LocalDate getStartLocalDate() {
        if (startDate == null || startDate.isEmpty()) {
            return null;
        }
        YearMonth yearMonth = YearMonth.parse(startDate, FORMATTER);
        return yearMonth.atDay(1);
    }

    public LocalDate getEndLocalDate() {
        if (endDate == null || endDate.isEmpty()) {
            return null;
        }
        YearMonth yearMonth = YearMonth.parse(endDate, FORMATTER);
        return yearMonth.atEndOfMonth();
    }
}
