package insurepro.claim_service.Repository;

import com.insurepro.claim_service.Entity.ClaimEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 

import java.time.Instant; 

@Repository 
public interface ClaimRepo extends JpaRepository<ClaimEntity,Long> {

 Long countByPolicyIdaAndCreatedAtAfter(Long policyId, Instant thirtyDaysAgo); 
}