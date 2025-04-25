package com.example.CloudBalanceBackend.service;

import com.example.CloudBalanceBackend.dto.CostExplorerGroupFilterDTO;

import java.util.List;

public interface CostExplorerGroupService {
    List<CostExplorerGroupFilterDTO> getAllAvailableServices();
}
