package com.insurepro.analytics_service.analtyics_service.FeignClient;

import com.insurepro.analytics_service.analtyics_service.Model.ClaimStatsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "claim-service")
public interface ClaimServiceClient {

   @GetMapping("/claim/claimSubmission/stats/{customerId}")
   public ClaimStatsResponse claimStatus(@PathVariable("customerId") long customerId);
}
