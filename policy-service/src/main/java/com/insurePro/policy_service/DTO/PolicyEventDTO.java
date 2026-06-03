package com.insurePro.policy_service.DTO;

import lombok.Data;

import java.util.Map;

@Data
public class PolicyEventDTO {
    private Long policyId;
    private Long customerId;
    private Long agentId;
    private String coverageType;
    private Double premium;
    private Double tax;
    private String status;
    private Map<String, String> payLoad;
}