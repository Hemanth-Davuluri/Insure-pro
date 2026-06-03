package com.insurepro.analytics_service.analtyics_service.FeignClient;

import com.insurepro.analytics_service.analtyics_service.Model.ClaimStatsResponse;
import com.insurepro.analytics_service.analtyics_service.Model.PolicyStatsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "policy-service")
public interface PolicyServiceClient {

    @GetMapping("/policy/policySubscription/stats")
    PolicyStatsResponse policyStatus(@RequestParam("customerId") long customerId);
}
