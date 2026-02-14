package com.insurePro.policy_service.MapperClasses;


import com.insurePro.policy_service.DTO.CoverageRule;
import com.insurePro.policy_service.Entity.CoverageRuleEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoverageRuleMapper {

//    DTO to Entity
    CoverageRuleEntity coverageRuleToCoverageRuleEntity(CoverageRule coverageRule);

//    Entity to Dto
    CoverageRule coverageRuleEntityToCoverageRule(CoverageRuleEntity coverageRuleEntity);
    List<CoverageRule> coverageRuleEntityToCoverageRuleList(List<CoverageRuleEntity> coverageRuleEntityList);


}
