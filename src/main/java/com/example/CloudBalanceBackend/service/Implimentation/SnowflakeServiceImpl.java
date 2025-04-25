package com.example.CloudBalanceBackend.service.Implimentation;

import com.example.CloudBalanceBackend.dto.CostExplorerDTO;
import com.example.CloudBalanceBackend.dto.SnowflakeDto;
import com.example.CloudBalanceBackend.repository.SnowflakeRepository;
import com.example.CloudBalanceBackend.service.SnowflakeService;
import com.example.CloudBalanceBackend.util.query.QueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SnowflakeServiceImpl implements SnowflakeService {

    private final SnowflakeRepository snowflakeRepository;

    public SnowflakeServiceImpl(SnowflakeRepository snowflakeRepository) {
        this.snowflakeRepository = snowflakeRepository;
    }

    @Override
    public List<Map<String, Object>> fetchAllAccounts() {
        return snowflakeRepository.getAllAccountsData();
    }

    @Override
    public List<String> fetchAllServices() {
        return snowflakeRepository.getAllServices();
    }

    @Override
    public List<Map<String, Object>> fetchGraphData(SnowflakeDto snowflakeDto) {
        String query = QueryBuilder.queryConverter(snowflakeDto);
        return snowflakeRepository.getDynamicGraphData(query);
    }
}
