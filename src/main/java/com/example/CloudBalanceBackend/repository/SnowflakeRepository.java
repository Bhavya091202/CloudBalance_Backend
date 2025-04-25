package com.example.CloudBalanceBackend.repository;

import com.example.CloudBalanceBackend.dto.CostExplorerDTO;
import software.amazon.awssdk.services.ec2.endpoints.internal.Value;

import java.util.List;
import java.util.Map;

public interface SnowflakeRepository {
    List<Map<String, Object>>  getAllAccountsData();
//    List<Map<String, Object>> queryWithFilters(
//            String table,
//            String where,
//            String groupBy,
//            String having,
//            String orderBy,
//            Integer limit
//    );
    List<String> getAllServices();

    List<Map<String, Object>> getDynamicGraphData(String query);
}
