package com.example.CloudBalanceBackend.controller;

import com.example.CloudBalanceBackend.dto.SnowflakeDto;
import com.example.CloudBalanceBackend.service.SnowflakeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/snowflake")
public class SnowflakeController {

    private final SnowflakeService snowflakeService;

    public SnowflakeController(SnowflakeService snowflakeService) {
        this.snowflakeService = snowflakeService;
    }

//    @GetMapping("/accounts")
//    public ResponseEntity<List<Map<String, Object>>> getAccounts() {
//        return ResponseEntity.ok(snowflakeService.fetchAllAccounts());
//    }

//    @GetMapping("/services")
//    public  ResponseEntity<List<String>> getServices() {
//        return ResponseEntity.ok(snowflakeService.fetchAllServices());
//    }

    @GetMapping("/filterData")
    public ResponseEntity<List<String>> getFilterData(@RequestParam String filterName) {
        return ResponseEntity.ok(snowflakeService.fetchFilterData(filterName));
    }

    @PostMapping("/graph-data")
    public ResponseEntity<List<Map<String, Object>>> getGraphData(@RequestBody SnowflakeDto snowflakeDto) {
        return  ResponseEntity.ok(snowflakeService.fetchGraphData(snowflakeDto));
    }
}
