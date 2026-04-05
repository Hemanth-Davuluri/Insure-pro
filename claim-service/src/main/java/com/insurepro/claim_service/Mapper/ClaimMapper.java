package insurepro.claim_service.Mapper;


import com.insurepro.claim_service.Entity.ClaimEntity;
import insurepro.claim_service.DTO.Claim;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") 
public interface ClaimMapper { 

 ClaimEntity DTOtoEntity(Claim claim);
 Claim EntityToDto(ClaimEntity claimEntity);
}