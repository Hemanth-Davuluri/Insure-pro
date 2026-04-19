package com.insurepro.auth_service.Repo;

import com.insurepro.auth_service.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepo extends JpaRepository<CustomerEntity,Long> {
    CustomerEntity findByUserName(String username);
}
