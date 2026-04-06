package com.insurePro.policy_service.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CoverageRule {

    private Long id;
    private String coverageType;
    private List<String> requiredFields;
    private Double premium;
    private Double tax;

    @Override
    public String toString(){

        return "";
    }
}
