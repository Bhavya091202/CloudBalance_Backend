package com.example.CloudBalanceBackend.service.Implimentation;

import com.example.CloudBalanceBackend.dto.SnowflakeDto;
import com.example.CloudBalanceBackend.repository.SnowflakeRepository;
import com.example.CloudBalanceBackend.service.SnowflakeService;
import com.example.CloudBalanceBackend.util.query.QueryBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
        LocalDate startDate = snowflakeDto.getStartLocalDate();
        LocalDate endDate = snowflakeDto.getEndLocalDate();

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start Date and End Date must not be null or invalid.");
        }

        String query = QueryBuilder.queryConverter(snowflakeDto, startDate, endDate);
        return snowflakeRepository.getDynamicGraphData(query);
    }

    @Override
    public List<String> fetchFilterData(String filterName) {
        return snowflakeRepository.fetchDistinctValues(filterName);
    }
}
