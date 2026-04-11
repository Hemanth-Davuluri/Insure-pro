package com.insurepro.claim_service.Controller;

import com.insurepro.claim_service.DTO.Claim;
import com.insurepro.claim_service.Service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AgentController {
    
    private final AgentService agentService;

    @PostMapping("/reviewClaim")
    public ResponseEntity<String> reviewClaim(@RequestParam("claimId") Long claimId,
                                              @RequestParam("action") String claimStatus) {
        String status = agentService.reviewClaim(claimId, claimStatus);
        return ResponseEntity.ok().body(status);
    }

    @PostMapping("/getClaimsByStatus")
    public ResponseEntity<Page<Claim>> getClaimsByStatus(
            @RequestParam("claimStatus") String claimStatus,
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize) {
        Page<Claim> claim = agentService.getClaimsByStatus(claimStatus, page, pageSize);
        return ResponseEntity.ok().body(claim);
    }
}
