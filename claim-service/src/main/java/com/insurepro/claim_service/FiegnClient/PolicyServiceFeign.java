package com.insurepro.claim_service.FiegnClient;

import com.insurepro.claim_service.DTO.PolicyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "policy-service") 
public interface PolicyServiceFeign {

 @GetMapping("/policySubscription/getPoliciesSubscribedByaCustomer/{customerId}/{coverageType}")
 public PolicyResponse serviceName(@PathVariable("customerId") Long customerId, @PathVariable("coverageType") String coverageType);
}