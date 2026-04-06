package com.insurePro.policy_service.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class Customer {

    private Long policyId;
    private Long customerId;
    private String coverageType;
    private Map<String, String> payLoad;


    @Override
    public String toString(){

        return "";
    }
}
