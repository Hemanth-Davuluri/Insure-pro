package insurepro.claim_service.CustomerService;

import insurepro.claim_service.DTO.Claim;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CustomerService {
    Claim claimSubmission(MultipartFile image, Claim claim) throws IOException;
}
