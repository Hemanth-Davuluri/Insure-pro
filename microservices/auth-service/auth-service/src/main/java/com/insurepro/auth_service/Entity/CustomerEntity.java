package com.insurepro.auth_service.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    @ElementCollection
    private List<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
