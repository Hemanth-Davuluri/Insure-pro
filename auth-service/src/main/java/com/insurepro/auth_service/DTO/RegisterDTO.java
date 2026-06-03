package com.insurepro.auth_service.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class RegisterDTO {

    @NotBlank(message = "userName is Required")
    @Size(min = 5 , max = 15 , message = "username should be in length of 5 to 15 characters ")
    private String userName;

    @NotBlank(message = "Password is empty")
    @Size(min=8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    private String phoneNumber;


    private List<String> roles;
}