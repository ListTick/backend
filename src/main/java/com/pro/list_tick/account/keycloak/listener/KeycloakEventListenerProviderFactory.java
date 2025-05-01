package com.pro.list_tick.account.keycloak.listener;

import com.pro.list_tick.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

@RequiredArgsConstructor
@Slf4j
public class KeycloakEventListenerProviderFactory implements EventListenerProviderFactory {

    private final AccountService accountService;

    @Override
    public EventListenerProvider create(KeycloakSession session) {
        log.debug("Creating EventListenerProvider for the keycloak session: {}", session);
        return new KeycloakEventListenerProvider(accountService);
    }

    @Override
    public void init(Config.Scope scope) {
        // No initialization logic
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // No post-initialization logic
    }

    @Override
    public String getId() {
        return "custom-registration-listener";
    }

    @Override
    public void close() {
        // No cleanup logic
    }

}
