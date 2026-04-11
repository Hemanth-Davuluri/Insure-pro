package com.insurepro.claim_service.Service;

import com.insurepro.claim_service.DTO.Claim;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AgentService {

    String reviewClaim(Long claimId, String claimStatus );
    Page<Claim> getClaimsByStatus(String claimStatus, int page, int pageSize);
}
