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
     Long countByPolicyIdAndDateAfter(Long policyId, Instant thirtyDaysAgo);
     List<ClaimEntity> findByCustomerId(Long userId);
     ClaimEntity findAllByClaimIdAndCustomerId(Long claimId, Long CustomerId);
     Page<ClaimEntity> findByStatus(String status, Pageable pageable);
}