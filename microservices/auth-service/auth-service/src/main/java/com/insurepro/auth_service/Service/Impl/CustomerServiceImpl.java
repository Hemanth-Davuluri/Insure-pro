package com.insurepro.auth_service.Service.Impl;

import com.insurepro.auth_service.DTO.RegisterDTO;
import com.insurepro.auth_service.DTO.RegisterResponseDTO;
import com.insurepro.auth_service.Entity.CustomerEntity;
import com.insurepro.auth_service.Repo.CustomersRepo;
import com.insurepro.auth_service.Service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomersRepo customersRepo;

    @Autowired
    private ModelMapper modelMapper;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public RegisterResponseDTO register(RegisterDTO register) {
        CustomerEntity userDetails = modelMapper.map(register, CustomerEntity.class);
        userDetails.setPassword(passwordEncoder.encode(register.getPassword()));
        userDetails.setCreatedAt(LocalDateTime.now());
        userDetails.setRoles(Arrays.asList("user"));
        CustomerEntity save = customersRepo.save(userDetails);
        return modelMapper.map(save, RegisterResponseDTO.class);
    }
}
