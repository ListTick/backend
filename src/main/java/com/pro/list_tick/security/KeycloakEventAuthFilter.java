package com.pro.list_tick.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class KeycloakEventAuthFilter extends OncePerRequestFilter {

    private final String eventSecret;

    public KeycloakEventAuthFilter(String eventSecret) {
        this.eventSecret = eventSecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if ("/api/account".equals(request.getRequestURI())
                && "POST".equals(request.getMethod())) {
            String secret = request.getHeader("X-Keycloak-Secret");
            if (!eventSecret.equals(secret)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}