package com.insurepro.analytics_service.analtyics_service.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsResponse {

    private ClaimStatsResponse claimStatsResponse;
    private PolicyStatsResponse policyStatsResponse;
}
