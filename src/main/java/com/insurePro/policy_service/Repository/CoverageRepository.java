package com.insurePro.policy_service.Repository;

import com.insurePro.policy_service.Entity.CoverageRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoverageRepository extends JpaRepository<CoverageRuleEntity,Long>{

    CoverageRuleEntity findByCoverageType(String coverageType);

}
