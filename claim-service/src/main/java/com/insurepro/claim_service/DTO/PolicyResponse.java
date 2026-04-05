package insurepro.claim_service.DTO;

import lombok.Getter;
import lombok.Setter; 
import lombok.ToString; 

@Getter 
@Setter 
@ToString 
public class PolicyResponse { 

 private String coverageType; 
 private Long premium; 
 private String status; 

}