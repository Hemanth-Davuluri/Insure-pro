package com.insurepro.auth_service.Service;

import com.insurepro.auth_service.DTO.RegisterResponseDTO;
import com.insurepro.auth_service.DTO.RegisterDTO;

public interface CustomerService {
    RegisterResponseDTO register(RegisterDTO register);
}
