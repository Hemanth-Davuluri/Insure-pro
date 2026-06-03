package com.insurePro.policy_service.Repository;

import com.insurePro.policy_service.Entity.PolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicySubRepository extends JpaRepository<PolicyEntity,Long> {
    PolicyEntity findByCustomerIdAndCoverageType(Long customerId, String coverageType);
    long countByCustomerId(Long customerId);
    long countByCustomerIdAndStatus(Long customerId, String status);
    @Query("""
           select sum(p.premium) from PolicyEntity p
           where p.customerId = :customerId and p.status=:status""")
    Double getPremium(@Param("customerId") Long customerId, @Param("status") String status);
}
