package com.example.CloudBalanceBackend.awsClient;

import com.example.CloudBalanceBackend.config.AWSCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rds.RdsClient;

public class RDSClientBuilder {
    public static RdsClient buildRDSClient(String roleArn) {
        return RdsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(AWSCredentials.getSessionCredentials(roleArn))
                .build();
    }
}
