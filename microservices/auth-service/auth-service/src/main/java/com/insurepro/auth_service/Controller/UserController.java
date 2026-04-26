package com.insurepro.auth_service.Controller;

import com.insurepro.auth_service.DTO.LoginDTO;
import com.insurepro.auth_service.DTO.LoginResponse;
import com.insurepro.auth_service.DTO.RegisterDTO;
import com.insurepro.auth_service.DTO.RegisterResponseDTO;
import com.insurepro.auth_service.Secuirty.JWTUtil;
import com.insurepro.auth_service.Service.CustomerService;
import com.insurepro.auth_service.Service.Impl.UserInfoConfigManager;
import com.insurepro.auth_service.Utils.ResponseHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomerService customerService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO login){
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

        UserDetails principal = (UserDetails) authenticate.getPrincipal();
        String token = jwtUtil.generateToken(
                principal.getUsername(),
                principal.getAuthorities()
                        .stream()
                        .map(a -> a.getAuthority().replace("ROLE_", ""))
                        .toList()
        );
        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(token)
                .build();
        return ResponseHandler.generateResponse("user logged in successfully", HttpStatus.OK, loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO register){
        RegisterResponseDTO registered = customerService.register(register);
        return ResponseHandler.generateResponse("register successfully", HttpStatus.OK, registered);
    }
}
