package insurepro.claim_service.Service.Impl;

import com.insurepro.claim_service.Entity.ClaimEntity;
import insurepro.claim_service.DTO.Claim;
import insurepro.claim_service.Mapper.ClaimMapper;
import insurepro.claim_service.Repository.ClaimRepo;
import insurepro.claim_service.Service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final ClaimRepo claimRepo;
    private final ClaimMapper claimMapper;

    @Override
    public String reviewClaim(Long claimId, String claimStatus ) {
        ClaimEntity allByClaimIdAndCustomerID = claimRepo.findById(claimId)
                .orElseThrow(() -> new RuntimeException("claim id not found"+ claimId));
        allByClaimIdAndCustomerID.setStatus(claimStatus);
        ClaimEntity save = claimRepo.save(allByClaimIdAndCustomerID);
        if (save != null) {
            return "Claim Status has been updated successfully" + claimId + "--" + claimStatus;
        }
        return "claim status update failure" + claimId + "-->"+ claimStatus;
    }

    @Override
    public Page<Claim> getClaimsByStatus(String claimStatus, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdDate").descending());
        Page<ClaimEntity> byStatus = claimRepo.findByStatus(claimStatus, pageable);
        return byStatus.map(claimMapper::EntityToDto);
    }
}
