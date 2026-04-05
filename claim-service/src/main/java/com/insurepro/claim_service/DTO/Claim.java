package insurepro.claim_service.DTO;

import lombok.Getter;
import lombok.Setter; 

@Getter 
@Setter 
public class Claim { 
 private Long id; 
 private Long customerId; 
 private String coverageType; 
 private Long policyId; 
 private Double amount; 
 private String description; 

 @Override 
 public String toString() { 
 return "Claim{" + 
 "id=" + id + 
 ", policyId=" + policyId + 
 ", amount=" + amount + 
 ", description='" + description + '\'' + 
 '}'; 
 } 
}