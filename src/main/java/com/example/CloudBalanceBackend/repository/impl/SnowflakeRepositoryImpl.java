package com.example.CloudBalanceBackend.repository.impl;

import com.example.CloudBalanceBackend.dto.CostExplorerDTO;
import com.example.CloudBalanceBackend.repository.SnowflakeRepository;
import com.example.CloudBalanceBackend.util.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SnowflakeRepositoryImpl implements SnowflakeRepository {

    private final JdbcTemplate snowflakeJdbcTemplate;

    public SnowflakeRepositoryImpl(@Qualifier("snowflakeJdbcTemplate") JdbcTemplate snowflakeJdbcTemplate) {
        this.snowflakeJdbcTemplate = snowflakeJdbcTemplate;
    }

    @Override
    public List<Map<String, Object>> getAllAccountsData() {
        String sql = "Select * from COST_EXPLORER limit 1000";
        return snowflakeJdbcTemplate.queryForList(sql);
    }

    @Override
    public List<String> getAllServices() {
        String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_NAME = 'COST_EXPLORER' AND TABLE_SCHEMA = 'COST'";
        return snowflakeJdbcTemplate.query(sql,  (rs, rowNum) -> rs.getString("COLUMN_NAME"));
    }

    @Override
    public List<Map<String, Object>> getDynamicGraphData(String query) {
        return snowflakeJdbcTemplate.queryForList(query);
    }
}
