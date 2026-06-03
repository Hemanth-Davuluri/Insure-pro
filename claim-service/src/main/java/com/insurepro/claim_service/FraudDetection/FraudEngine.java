package com.insurepro.claim_service.FraudDetection;

import com.insurepro.claim_service.ClaimEnum.ClaimStatus;
import com.insurepro.claim_service.DTO.Claim;
import com.insurepro.claim_service.DTO.PolicyResponse;
import com.insurepro.claim_service.Repository.ClaimRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class FraudEngine {

    @Autowired
    private ClaimRepo claimRepo;

    public String getRiskDetails(PolicyResponse policyResponses, Claim claim) {
        /*
         * is about getting the date of 30 days back.
         * retrieving count of claims based on that.
         */
        Instant thirtyDaysAgo = Instant.now().minus(30, ChronoUnit.DAYS);
        Long recent = claimRepo.countByPolicyIdAndDateAfter(claim.getPolicyId(), thirtyDaysAgo);
        /*
         * greater than 3 claims in 30 days ==>"HIGH_RISK"
         */
        if (recent > 3) {
            return ClaimStatus.HIGH_RISK.toString();
        }
        /*
         * Claim amount > policy coverage ==> "REJECTED"
         */
        if (policyResponses.getPremium() > claim.getAmount()) {
            return ClaimStatus.REJECTED.toString();
        }
        return ClaimStatus.PENDING.toString();
    }
}