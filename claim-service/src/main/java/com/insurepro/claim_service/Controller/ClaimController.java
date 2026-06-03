package com.insurepro.claim_service.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurepro.claim_service.DTO.Claim;
import com.insurepro.claim_service.DTO.ClaimDto;
import com.insurepro.claim_service.DTO.ClaimStatsResponse;
import com.insurepro.claim_service.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/claim/claimSubmission")
public class ClaimController {

    private final CustomerService customerService;

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<?> claimSubmission(
            @RequestPart("image") MultipartFile image ,
            @RequestPart("claim") String claimDetails)throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        Claim claim = mapper.readValue(claimDetails, Claim.class);
        Claim details = customerService.claimSubmission(image,claim);
        System.out.println(claim.toString());
        return ResponseEntity.ok().body("Done");
    }

    @GetMapping("/stats/{customerId}")
    public ResponseEntity<ClaimStatsResponse> getClaimStats(
            @PathVariable Long customerId) {
        return ResponseEntity.ok(
                customerService.getClaimStats(customerId)
        );
    }
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ClaimDto>> getClaimsByCustomer(
            @PathVariable Long customerId) {
        return ResponseEntity.ok(
                customerService.findByCustomerId(customerId)
        );
    }
}
