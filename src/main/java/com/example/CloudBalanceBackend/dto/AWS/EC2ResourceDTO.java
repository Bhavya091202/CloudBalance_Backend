package com.example.CloudBalanceBackend.dto.AWS;

import lombok.Data;

@Data
//@AllArgsConstructor
public class EC2ResourceDTO {
    private String resourceId;
    private String resourceName;
    private String region;
    private String status;
}
