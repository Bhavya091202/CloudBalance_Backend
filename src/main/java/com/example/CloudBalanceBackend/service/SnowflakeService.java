package com.example.CloudBalanceBackend.service;

import com.example.CloudBalanceBackend.dto.SnowflakeDto;

import java.util.List;
import java.util.Map;

public interface SnowflakeService {
    List<Map<String, Object>> fetchAllAccounts();
    List<String> fetchAllServices();
    List<Map<String, Object>> fetchGraphData(SnowflakeDto snowflakeDto);
    List<String> fetchFilterData(String filterName);
}
