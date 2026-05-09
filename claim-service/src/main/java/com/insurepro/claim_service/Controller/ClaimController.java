package com.insurepro.claim_service.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurepro.claim_service.DTO.Claim;
import com.insurepro.claim_service.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
}
