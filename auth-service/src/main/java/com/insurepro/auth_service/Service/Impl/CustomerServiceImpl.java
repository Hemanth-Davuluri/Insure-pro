package com.insurepro.auth_service.Service.Impl;

import com.insurepro.auth_service.DTO.RegisterDTO;
import com.insurepro.auth_service.DTO.RegisterResponseDTO;
import com.insurepro.auth_service.Entity.CustomerEntity;
import com.insurepro.auth_service.Repo.CustomersRepo;
import com.insurepro.auth_service.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomersRepo customersRepo;
    private final ModelMapper modelMapper;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public RegisterResponseDTO register(RegisterDTO register) {
        CustomerEntity userDetails = modelMapper.map(register, CustomerEntity.class);
        List<String> roles = register.getRoles();
        List<String> userRoles  = roles.stream()
                .filter(role -> !role.isBlank() && role != null)
                .map(String::trim)
                .map(String::toUpperCase)
                .toList();
        userDetails.setPassword(passwordEncoder.encode(register.getPassword()));
        userDetails.setCreatedAt(LocalDateTime.now());
        userDetails.setRoles(userRoles);
        CustomerEntity save = customersRepo.save(userDetails);
        return modelMapper.map(save, RegisterResponseDTO.class);
    }
}