package com.insurePro.policy_service.MapperClasses;

import com.insurePro.policy_service.DTO.PolicyDTO;
import com.insurePro.policy_service.Entity.PolicyEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerSubscriptionMapper {

//  Entity -> DTO
    PolicyDTO toDTO(PolicyEntity entity);
    // DTO -> Entity
    PolicyEntity toEntity(PolicyDTO dto);

    // List conversions
    List<PolicyDTO> toDTOList(List<PolicyEntity> entities);
    List<PolicyEntity> toEntityList(List<PolicyDTO> dtos);
}
