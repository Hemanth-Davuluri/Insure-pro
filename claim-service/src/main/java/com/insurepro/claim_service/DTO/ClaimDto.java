package com.insurepro.claim_service.DTO;

import com.insurepro.claim_service.ClaimEnum.ClaimStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClaimDto {
    private Long claimNumber;
    private Long customerId;
    private Long policyId;
    private Double amount;
    private String status;
    private Instant createdDate;
}
