package com.insurepro.claim_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClaimStatsResponse {

    private long totalClaims;
    private long approvedClaims;
    private long rejectedClaims;
    private long pendingClaims;
    private double totalClaimAmount;
    private double approvedClaimAmount;
}