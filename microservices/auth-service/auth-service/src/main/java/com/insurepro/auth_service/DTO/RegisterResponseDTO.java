package com.insurepro.auth_service.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterResponseDTO {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
