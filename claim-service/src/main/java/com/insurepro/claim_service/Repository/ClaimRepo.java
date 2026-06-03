package com.insurepro.claim_service.Repository;

import com.insurepro.claim_service.Entity.ClaimEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository 
public interface ClaimRepo extends JpaRepository<ClaimEntity,Long> {
     Long countByPolicyIdAndDateAfter(Long policyId, Instant thirtyDaysAgo);
     List<ClaimEntity> findByCustomerId(Long userId);
     ClaimEntity findAllByClaimIdAndCustomerId(Long claimId, Long CustomerId);
     Page<ClaimEntity> findByStatus(String status, Pageable pageable);

     Long countByCustomerId(Long customerId);
     Long countByCustomerIdAndStatus(Long customerId, String status);

     @Query("""
       SELECT COALESCE(SUM(c.amount),0)
       FROM ClaimEntity c
       WHERE c.customerId = :customerId
       """)
     Double getTotalClaimAmount(@Param("customerId") Long customerId);

     @Query("""
       SELECT COALESCE(SUM(c.amount),0)
       FROM ClaimEntity c
       WHERE c.customerId = :customerId
       AND c.status = 'APPROVED'
       """)
     Double getApprovedClaimAmount(@Param("customerId") Long customerId);
}