package com.insurepro.claim_service.FiegnClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "policy-service") 
public interface PolicyServiceFeign<PolicyResponse> {

 @GetMapping("policySubscription/getPoliciesSubscribedByaCustomer/{customerId}/{coverageType}") 
 public PolicyResponse serviceName(@PathVariable("customerId") Long customerId, @PathVariable("coverageType") String coverageType);
}