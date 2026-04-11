package com.insurepro.claim_service.Mapper;


import com.insurepro.claim_service.Entity.ClaimEntity;
import com.insurepro.claim_service.DTO.Claim;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring") 
public interface ClaimMapper { 

 ClaimEntity DTOtoEntity(Claim claim);
 Claim EntityToDto(ClaimEntity claimEntity);
 List<Claim> EntityToDtoList(List<ClaimEntity> claimEntities);
}