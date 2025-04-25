package com.example.CloudBalanceBackend.util.config;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;
import software.amazon.awssdk.services.sts.model.AssumeRoleResponse;
import software.amazon.awssdk.services.sts.model.Credentials;

import java.util.UUID;

public class AWSCredentials {

    public static AwsCredentialsProvider getSessionCredentials(String roleArn) {
        StsClient stsClient = StsClient.create();
        AssumeRoleRequest roleRequest = AssumeRoleRequest.builder()
                .roleArn(roleArn)
                .roleSessionName("session-" + UUID.randomUUID())
                .durationSeconds(3600)
                .build();

        AssumeRoleResponse roleResponse = stsClient.assumeRole(roleRequest);
        Credentials credentials = roleResponse.credentials();

        return StaticCredentialsProvider.create(
                AwsSessionCredentials.create(
                        credentials.accessKeyId(),
                        credentials.secretAccessKey(),
                        credentials.sessionToken()
                )
        );
    }
}
