package com.pro.list_tick.account.service;

import com.pro.list_tick.account.repository.settings.AccountSettingsRepository;
import com.pro.list_tick.account.repository.keycloak.KeycloakRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final ApplicationEventPublisher publisher;
    private final AccountSettingsRepository accountSettingsRepository;
    private final KeycloakRepository keycloakRepository;

    @Override
    public UUID getUUIDbyEmail(String email) {
        return null;
    }

//    @Transactional
//    public void createAccount(AccountDto accountDto) {
//        AccountSettings accountSettings = accountSettingsMapper.toEntity(accountDto);
//        AccountSettings savedAccountSettings = accountSettingsRepository.save(accountSettings);
//
//        publisher.publishEvent(new AccountCreatedEvent(savedAccountSettings.getId()));
//    }

}
