package com.insurePro.policy_service.DTO;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@ToString
public class PolicyDTO {

    private Long policyId;
    private Long customerId;
    private Long agentId;
    private String coverageType;
    private Double premium;
    private Double tax;
    private String status;
    private Map<String, String> payLoad;

    @Override
    public String toString(){

        return "";
    }

}
