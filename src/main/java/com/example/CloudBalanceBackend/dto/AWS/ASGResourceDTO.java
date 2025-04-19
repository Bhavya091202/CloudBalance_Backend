package com.example.CloudBalanceBackend.dto.AWS;

import lombok.Data;

//@AllArgsConstructor
@Data
public class ASGResourceDTO {
    private String resourceId;
    private String resourceName;
    private String region;
    private Integer desiredCapacity;
    private Integer minSize;
    private Integer maxSize;
    private String status;
}
