package com.insurepro.claim_service.Service.Impl;

import com.insurepro.claim_service.ClaimEnum.ClaimStatus;
import com.insurepro.claim_service.DTO.ClaimDto;
import com.insurepro.claim_service.DTO.ClaimStatsResponse;
import com.insurepro.claim_service.Entity.ClaimEntity;
import com.insurepro.claim_service.Service.CustomerService;
import com.insurepro.claim_service.DTO.Claim;
import com.insurepro.claim_service.DTO.PolicyResponse;
import com.insurepro.claim_service.FiegnClient.PolicyServiceFeign;
import com.insurepro.claim_service.FraudDetection.FraudEngine;
import com.insurepro.claim_service.Mapper.ClaimMapper;
import com.insurepro.claim_service.Repository.ClaimRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ClaimRepo claimRepo;
    private final ClaimMapper claimMapper;
    private final PolicyServiceFeign policyServiceFeign;
    private final FraudEngine fraudEngine;

    private final String filePath = "C:\\Generated files\\claim-service images";

    @Override
    public Claim claimSubmission(MultipartFile image, Claim claim) throws IOException {
        /*
         * about creating a folder and storing it in a specific path
         * if folder doesn't exist it will create a folder with the name of policy claimId
         * inside that the imaged will be stored
         */
        String imageFilePath = filePath + File.separator + claim.getPolicyId();
        String path = createOrGetPath(imageFilePath);
        String imagePath = path + File.separator + image.getOriginalFilename();
        image.transferTo(new File(imagePath));
        com.insurepro.claim_service.Entity.ClaimEntity claimEntity = claimMapper.DTOtoEntity(claim);
        claimEntity.setImagePath(path);
        /*
         * applying validation rules by calling fraud Engine .
         */
        PolicyResponse policyResponse = policyServiceFeign.serviceName(claim.getCustomerId(), claim.getCoverageType());
        String riskDetails = fraudEngine.getRiskDetails(policyResponse, claim);
        claimEntity.setStatus(riskDetails);
        claimEntity.setDate(Instant.now());
        System.out.println(policyResponse.toString());
        com.insurepro.claim_service.Entity.ClaimEntity save = claimRepo.save(claimEntity);
        return claimMapper.EntityToDto(save);
    }

    /*
     * creating a folder if path doesn't exist
     */
    public static String createOrGetPath(String filePath) throws IOException {
        File folder = new File(filePath);
        if (!folder.exists()) {
            boolean mkdirs = folder.mkdirs();
            if (mkdirs) {
                System.out.println("Created");
                return folder.getAbsolutePath();
            }
        }
        return folder.getAbsolutePath();
    }

    public List<Claim> getClaimsBasedOnUserID(Long userId) {
        List<ClaimEntity> claimedEntitesofUser = claimRepo.findByCustomerId(userId);
        if (CollectionUtils.isEmpty(claimedEntitesofUser)) {
           return Collections.emptyList();
        }
        List<Claim> claimsOfuser = claimMapper.EntityToDtoList(claimedEntitesofUser);
        return claimsOfuser;
    }

    @Override
    public ClaimStatsResponse getClaimStats(Long customerId) {

        Long totalClaims = claimRepo.countByCustomerId(customerId);
        Long approvedClaims = claimRepo.
                countByCustomerIdAndStatus(customerId, ClaimStatus.APPROVED.toString());
        Long pendingClaims = claimRepo.
                countByCustomerIdAndStatus(customerId, ClaimStatus.PENDING.toString());
        Long rejectedClaims = claimRepo.
                countByCustomerIdAndStatus(customerId, ClaimStatus.REJECTED.toString());
        Double totalClaimAmount = claimRepo.getTotalClaimAmount(customerId);
        Double approvedClaimAmount = claimRepo.getApprovedClaimAmount(customerId);

        return ClaimStatsResponse.builder()
                .totalClaims(totalClaims)
                .approvedClaims(approvedClaims)
                .rejectedClaims(rejectedClaims)
                .pendingClaims(pendingClaims)
                .totalClaimAmount(totalClaimAmount)
                .approvedClaimAmount(approvedClaimAmount)
                .build();
    }

    @Override
    public List<ClaimDto> findByCustomerId(Long customerId) {
        List<ClaimEntity> byCustomerId = claimRepo.findByCustomerId(customerId);
        return byCustomerId.stream().map(claimEntity -> ClaimDto.builder()
                        .claimNumber(claimEntity.getClaimId())
                        .customerId(claimEntity.getCustomerId())
                        .policyId(claimEntity.getPolicyId())
                        .amount(claimEntity.getAmount())
                        .status(claimEntity.getStatus())
                        .createdDate(claimEntity.getDate())
                        .build())
                .collect(Collectors.toList());
    }
}
