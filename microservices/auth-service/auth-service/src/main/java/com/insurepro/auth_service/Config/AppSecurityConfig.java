package com.insurepro.auth_service.Config;

import com.insurepro.auth_service.Secuirty.JWTFilter;
import com.insurepro.auth_service.Service.Impl.UserInfoConfigManager;
import com.insurepro.auth_service.Utils.AppConstants;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class AppSecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    private UserInfoConfigManager userInfoConfigManager;

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(request -> request
                        .requestMatchers( "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/h2-console/**", "/register", "/login").permitAll()
                        .requestMatchers("/api/test/public/hello/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/test/private/**").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated())
                // ✅ Disable CSRF ONLY for H2 console
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))

                // ✅ Allow H2 to render in browser frames
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                .addFilterBefore((Filter) jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(req ->
                req.anyRequest()
                        .permitAll()).csrf(AbstractHttpConfigurer::disable).build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userInfoConfigManager);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
