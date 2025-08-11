package com.pro.list_tick.security;

import com.pro.list_tick.shared.CurrentAccountAPI;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentAccountAPIImpl implements CurrentAccountAPI {

    public UUID getCurrentAccountId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof JwtAuthenticationToken)) {
            throw new IllegalStateException("No JWT authentication token found");
        }
        Jwt jwt = (Jwt) authentication.getCredentials();
        String accountId = jwt.getClaim("sub");
        if (accountId == null) {
            throw new IllegalStateException("No account ID found in JWT");
        }
        return UUID.fromString(accountId);
    }
}
