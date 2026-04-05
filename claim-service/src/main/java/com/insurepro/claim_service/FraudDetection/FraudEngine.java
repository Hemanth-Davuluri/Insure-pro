package insurepro.claim_service.FraudDetection;

import insurepro.claim_service.ClaimEnum.ClaimStatus;
import insurepro.claim_service.DTO.Claim;
import insurepro.claim_service.DTO.PolicyResponse;
import insurepro.claim_service.Repository.ClaimRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class FraudEngine {

    @Autowired
    private ClaimRepo claimRepo;

    public String getRiskDetails(PolicyResponse policyResponses, Claim claim) {
        /*
         * is about getting the date of 30 days back.
         * retrieving count of claims based on that.
         */
        Instant thirtyDaysAgo = Instant.now().minus(30, ChronoUnit.DAYS);
        Long recent = claimRepo.countByPolicyIdaAndCreatedAtAfter(claim.getPolicyId(), thirtyDaysAgo);
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