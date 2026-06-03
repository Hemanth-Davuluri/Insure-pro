package com.insurepro.auth_service.Service.Impl;

import com.insurepro.auth_service.Entity.CustomerEntity;
import com.insurepro.auth_service.Repo.CustomersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoConfigManager implements UserDetailsService {

    @Autowired
    private CustomersRepo customersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerEntity user = customersRepo.findByUserName(username);
        if (user != null) {
            return User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}