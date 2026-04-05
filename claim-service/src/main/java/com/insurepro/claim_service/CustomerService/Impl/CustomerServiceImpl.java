package insurepro.claim_service.CustomerService.Impl;

import insurepro.claim_service.CustomerService.CustomerService;
import insurepro.claim_service.DTO.Claim;
import insurepro.claim_service.DTO.PolicyResponse;
import insurepro.claim_service.FiegnClients.PolicyServiceFeign;
import insurepro.claim_service.FraudDetection.FraudEngine;
import insurepro.claim_service.Mapper.ClaimMapper;
import insurepro.claim_service.Repository.ClaimRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ClaimRepo claimRepo;

    @Autowired
    private ClaimMapper claimMapper;

    @Autowired
    private PolicyServiceFeign policyServiceFeign;

    @Autowired
    private FraudEngine fraudEngine;

    private final String filePath = "C:\\Generated files\\claim-service images";

    @Override
    public Claim claimSubmission(MultipartFile image, Claim claim) throws IOException, IOException {

        /*
         * about creating a folder and storing it in a specific path
         * if folder doesn't exist it will create a folder with the name of policy id
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
}
