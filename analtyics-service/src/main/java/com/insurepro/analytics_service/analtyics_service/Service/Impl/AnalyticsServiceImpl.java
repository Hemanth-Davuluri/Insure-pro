package com.insurepro.analytics_service.analtyics_service.Service.Impl;

import com.insurepro.analytics_service.analtyics_service.FeignClient.ClaimServiceClient;
import com.insurepro.analytics_service.analtyics_service.FeignClient.PolicyServiceClient;
import com.insurepro.analytics_service.analtyics_service.Model.AnalyticsResponse;
import com.insurepro.analytics_service.analtyics_service.Model.ClaimStatsResponse;
import com.insurepro.analytics_service.analtyics_service.Model.PolicyStatsResponse;
import com.insurepro.analytics_service.analtyics_service.Service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final ClaimServiceClient claimServiceClient;
    private final PolicyServiceClient policyServiceClient;

    @Override
    public AnalyticsResponse getAnalytics(long customerId) {
        ClaimStatsResponse claimStatsResponse = claimServiceClient.claimStatus(customerId);
        PolicyStatsResponse policyStatsResponse = policyServiceClient.policyStatus(customerId);
        return AnalyticsResponse.builder()
                .claimStatsResponse(claimStatsResponse)
                .policyStatsResponse(policyStatsResponse)
                .build();
    }
}
