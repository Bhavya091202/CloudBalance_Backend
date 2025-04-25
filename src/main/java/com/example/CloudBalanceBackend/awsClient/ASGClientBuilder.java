package com.example.CloudBalanceBackend.awsClient;

import com.example.CloudBalanceBackend.util.config.AWSCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.autoscaling.AutoScalingClient;

public class ASGClientBuilder {
    public static AutoScalingClient buildASGClient(String roleArn) {
        return AutoScalingClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(AWSCredentials.getSessionCredentials(roleArn))
                .build();
    }
}
