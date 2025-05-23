package com.example.CloudBalanceBackend.service;

import com.example.CloudBalanceBackend.dto.AWS.ASGResourceDTO;
import com.example.CloudBalanceBackend.dto.AWS.EC2ResourceDTO;
import com.example.CloudBalanceBackend.dto.AWS.RDSResourceDTO;

import java.util.List;

public interface AwsResourceService {
        List<EC2ResourceDTO> getEC2Instances(Long accountId);
        List<RDSResourceDTO> getRDSInstances(Long accountId);
        List<ASGResourceDTO> getASGInstances(Long accountId);
}
