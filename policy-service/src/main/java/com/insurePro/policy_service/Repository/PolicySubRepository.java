package com.insurePro.policy_service.Repository;

import com.insurePro.policy_service.Entity.PolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicySubRepository extends JpaRepository<PolicyEntity,Long> {
    PolicyEntity findByCustomerIdAndCoverageType(Long customerId, String coverageType);
}
