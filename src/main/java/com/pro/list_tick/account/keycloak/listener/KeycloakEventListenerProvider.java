package com.pro.list_tick.account.keycloak.listener;

import com.pro.list_tick.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class KeycloakEventListenerProvider implements EventListenerProvider {

    private final AccountService accountService;

    @Override
    public void onEvent(Event event) {
        log.debug("Received an event, type: {}, time: {}",
                event.getType(), event.getTime());

        if (event.getType() == EventType.REGISTER) {
            log.info("Processing Keycloak REGISTER event, accountId: {}", event.getUserId());
            final UUID accountId = UUID.fromString(event.getUserId());
            accountService.createAccountSettings(accountId);
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        log.debug("Received an admin event: {}, time: {}, resourcePath: {}",
                adminEvent.getOperationType(), adminEvent.getTime(), adminEvent.getResourcePath());
    }

    @Override
    public void close() {
        // This method is not implemented on purpose.
        // There aren't any actions needed for closing the Provider.
    }

}
