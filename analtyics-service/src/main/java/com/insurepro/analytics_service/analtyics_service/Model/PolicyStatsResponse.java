package com.insurepro.analytics_service.analtyics_service.Model;

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