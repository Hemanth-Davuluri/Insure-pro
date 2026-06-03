package com.insurePro.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;
        if(authHeader != null &&
                authHeader.startsWith("Bearer ")) {

            token = authHeader.substring(7);
            System.out.println("Token from common security "+request.getHeader("token"));
            System.out.println(token);
            username = jwtUtil.extractUsername(token);
        }

        if(username != null &&
                SecurityContextHolder.getContext()
                        .getAuthentication() == null) {

            List<String> roles =
                    jwtUtil.extractRoles(token);

            List<SimpleGrantedAuthority> authorities =
                    roles.stream()
                            .map(role ->
                                    new SimpleGrantedAuthority(
                                            "ROLE_" + role))
                            .toList();

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
