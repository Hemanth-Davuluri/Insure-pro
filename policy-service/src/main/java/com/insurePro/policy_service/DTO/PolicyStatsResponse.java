package com.insurePro.policy_service.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolicyStatsResponse {

    private Long totalPolicies;
    private Long activePolicies;
    private Double totalPremium; // Used Double to safely handle large currency/premium amounts
    
}