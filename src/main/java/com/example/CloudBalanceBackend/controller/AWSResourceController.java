package com.example.CloudBalanceBackend.controller;

import com.example.CloudBalanceBackend.dto.AWS.ASGResourceDTO;
import com.example.CloudBalanceBackend.dto.AWS.EC2ResourceDTO;
import com.example.CloudBalanceBackend.dto.AWS.RDSResourceDTO;
import com.example.CloudBalanceBackend.service.AwsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/aws")
public class AWSResourceController {

    @Autowired
    private AwsResourceService awsResourceService;

    @GetMapping("/ec2")
    public ResponseEntity<List<EC2ResourceDTO>> getEC2(@RequestParam String arn) {
        return ResponseEntity.ok(awsResourceService.getEC2Instances(arn));
    }

    @GetMapping("/rds")
    public ResponseEntity<List<RDSResourceDTO>> getRDS(@RequestParam String arn) {
        return ResponseEntity.ok(awsResourceService.getRDSInstances(arn));
    }

    @GetMapping("/asg")
    public ResponseEntity<List<ASGResourceDTO>> getASG(@RequestParam String arn) {
        return ResponseEntity.ok(awsResourceService.getASGInstances(arn));
    }
}
