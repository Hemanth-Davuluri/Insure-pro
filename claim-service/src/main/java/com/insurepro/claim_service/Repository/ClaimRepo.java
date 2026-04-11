package com.insurepro.claim_service.Repository;

import com.insurepro.claim_service.Entity.ClaimEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository 
public interface ClaimRepo extends JpaRepository<ClaimEntity,Long> {

 Long countByPolicyIdaAndCreatedAtAfter(Long policyId, Instant thirtyDaysAgo);
 List<ClaimEntity> findByUserId(Long userId);
 ClaimEntity findAllByClaimIdAndCustomerID(Long claimId, Long CustomerID);
 Page<ClaimEntity> findByStatus(String status, Pageable pageable);
}