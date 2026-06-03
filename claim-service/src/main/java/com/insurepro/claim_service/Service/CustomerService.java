package com.insurepro.claim_service.Service;

import com.insurepro.claim_service.DTO.Claim;
import com.insurepro.claim_service.DTO.ClaimDto;
import com.insurepro.claim_service.DTO.ClaimStatsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    Claim claimSubmission(MultipartFile image, Claim claim) throws IOException;
    List<Claim> getClaimsBasedOnUserID(Long userId);

    ClaimStatsResponse getClaimStats(Long customerId);

    List<ClaimDto> findByCustomerId(Long customerId);
}
