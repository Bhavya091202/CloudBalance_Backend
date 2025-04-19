package com.example.CloudBalanceBackend.dto.AWS;

import lombok.Data;

//@AllArgsConstructor
@Data
public class RDSResourceDTO {
    private String resourceId;
    private String resourceName;
    private String engine;
    private String region;
    private String status;
}
