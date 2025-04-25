package com.example.CloudBalanceBackend.service.Implimentation;

import com.example.CloudBalanceBackend.awsClient.ASGClientBuilder;
import com.example.CloudBalanceBackend.awsClient.RDSClientBuilder;
import com.example.CloudBalanceBackend.dto.AWS.ASGResourceDTO;
import com.example.CloudBalanceBackend.dto.AWS.EC2ResourceDTO;
import com.example.CloudBalanceBackend.dto.AWS.RDSResourceDTO;
import com.example.CloudBalanceBackend.awsClient.EC2ClientBuilder;
import com.example.CloudBalanceBackend.repository.AccountRepository;
import com.example.CloudBalanceBackend.service.AwsResourceService;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.autoscaling.model.AutoScalingGroup;
import software.amazon.awssdk.services.ec2.model.Reservation;
import software.amazon.awssdk.services.ec2.model.Tag;
import software.amazon.awssdk.services.rds.model.DBInstance;

import java.util.ArrayList;
import java.util.List;

@Service
public class AWSResourceServiceImpl implements AwsResourceService {

    private final AccountRepository accountRepository;

    public AWSResourceServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<EC2ResourceDTO> getEC2Instances(Long accountId) {
        var roleArn = accountRepository
                .findByAccountId(accountId)
                .orElseThrow(()-> new RuntimeException("account not found"))
                .getArn();
        var ec2Client = EC2ClientBuilder.buildEC2Client(roleArn);
        var response = ec2Client.describeInstances();
        List<EC2ResourceDTO> result = new ArrayList<>();

        for (Reservation reservation : response.reservations()) {
            reservation.instances().forEach(instance -> {
                EC2ResourceDTO ec2ResourceDTO = new EC2ResourceDTO();
                ec2ResourceDTO.setResourceId(instance.instanceId());
                ec2ResourceDTO.setResourceName(instance
                        .tags()
                        .stream()
                        .filter(t -> t.key().equalsIgnoreCase("Name"))
                        .findFirst()
                        .map(Tag::value)
                        .orElse("N/A"));
                ec2ResourceDTO.setRegion(instance.placement().availabilityZone());
                ec2ResourceDTO.setStatus(instance.state().nameAsString());
                result.add(ec2ResourceDTO);
            });
        }
        return result;
    }

    @Override
    public List<RDSResourceDTO> getRDSInstances(Long accountId) {
        var roleArn = accountRepository
                .findByAccountId(accountId)
                .orElseThrow(()-> new RuntimeException("account not found"))
                .getArn();
        var rdsClient = RDSClientBuilder.buildRDSClient(roleArn);
        var response = rdsClient.describeDBInstances();
        List<RDSResourceDTO> result = new ArrayList<>();
        for (DBInstance instance : response.dbInstances()) {
            RDSResourceDTO rdsResourceDTO = new RDSResourceDTO();
            rdsResourceDTO.setResourceId(instance.dbInstanceIdentifier());
            rdsResourceDTO.setResourceName(instance.dbName() != null ? instance.dbName() : "N/A");
            rdsResourceDTO.setRegion(rdsClient.serviceClientConfiguration().region().id());
            rdsResourceDTO.setEngine(instance.engine());
            rdsResourceDTO.setStatus(instance.dbInstanceStatus());
            result.add(rdsResourceDTO);
        }
        return result;
    }

    @Override
    public List<ASGResourceDTO> getASGInstances(Long accountId) {
        var roleArn = accountRepository
                .findByAccountId(accountId)
                .orElseThrow(()-> new RuntimeException("account not found"))
                .getArn();
        var asgClient = ASGClientBuilder.buildASGClient(roleArn);
        var response = asgClient.describeAutoScalingGroups();
        List<ASGResourceDTO> result = new ArrayList<>();

        for (AutoScalingGroup group : response.autoScalingGroups()) {
            ASGResourceDTO dto = new ASGResourceDTO();
            dto.setResourceId(group.autoScalingGroupARN());
            dto.setResourceName(group.autoScalingGroupName());
            dto.setRegion(asgClient.serviceClientConfiguration().region().id());
            dto.setDesiredCapacity(group.desiredCapacity());
            dto.setMinSize(group.minSize());
            dto.setMaxSize(group.maxSize());
            dto.setStatus(group.status() != null ? group.status() : "N/A");
            result.add(dto);
        }

        return result;
    }

}