package com.example.CloudBalanceBackend.util.query;

import com.example.CloudBalanceBackend.dto.SnowflakeDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryBuilder {

    public static String queryConverter(SnowflakeDto dto, LocalDate startDate, LocalDate endDate) {
        StringBuilder query = new StringBuilder();

        String groupByField = dto.getGroupBy();
        if (groupByField == null || groupByField.trim().isEmpty()) {
            throw new IllegalArgumentException("Group by field cannot be null or empty.");
        }

        // Begin SELECT
        query.append("SELECT TO_CHAR(USAGESTARTDATE, 'YYYY-MM-DD') AS USAGE_DATE, ")
                .append(groupByField)
                .append(", SUM(LINEITEM_USAGEAMOUNT) AS TOTAL_USAGE_COST ");

        // FROM Clause
        query.append("FROM COST_EXPLORER ");

        // WHERE clause
        boolean hasWhereClause = false;
        if (startDate != null && endDate != null) {
            query.append("WHERE USAGESTARTDATE BETWEEN TO_DATE('")
                    .append(startDate)
                    .append("') AND TO_DATE('")
                    .append(endDate)
                    .append("') ");
            hasWhereClause = true;
        }

        // Additional filters
        Map<String, List<String>> filters = dto.getFilters();
        if (filters != null && !filters.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : filters.entrySet()) {
                String key = entry.getKey();
                List<String> values = entry.getValue();

                if (values != null && !values.isEmpty()) {
                    String inClause = values.stream()
                            .map(val -> "'" + val.replace("'", "''") + "'")
                            .collect(Collectors.joining(", "));

                    if (!hasWhereClause) {
                        query.append("WHERE ");
                        hasWhereClause = true;
                    } else {
                        query.append("AND ");
                    }

                    query.append(key).append(" IN (").append(inClause).append(") ");
                }
            }
        }

        // GROUP BY and ORDER BY
        query.append("GROUP BY TO_CHAR(USAGESTARTDATE, 'YYYY-MM-DD'), ")
                .append(groupByField)
                .append(" ")
                .append("ORDER BY USAGE_DATE, TOTAL_USAGE_COST DESC");

        return query.toString();
    }
}
