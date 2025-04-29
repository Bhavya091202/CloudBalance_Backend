package com.example.CloudBalanceBackend.repository;

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

    List<String> fetchDistinctValues(String filterName);
}
