package com.pro.list_tick.account.service;

import com.pro.list_tick.account.exception.AccountException;
import com.pro.list_tick.account.repository.keycloak.KeycloakRepository;
import com.pro.list_tick.shared.api.AccountAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountAPIImpl implements AccountAPI {

    private final KeycloakRepository keycloakRepository;

    @Override
    public UUID findIdByEmail(String email) {
        return keycloakRepository.findIdByEmail(email)
                .orElseThrow(() -> new AccountException("Account not found: " + email));
    }

}
