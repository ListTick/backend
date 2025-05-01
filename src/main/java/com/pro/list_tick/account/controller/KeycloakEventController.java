package com.pro.list_tick.account.controller;

import com.pro.list_tick.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/keycloak-events")
@RequiredArgsConstructor
@Slf4j
public class KeycloakEventController {

    private final AccountService accountService;

    @PostMapping()
    public ResponseEntity<Void> handleKeycloakEvent(@RequestBody Map<String, Object> event) {
        log.info("Received Keycloak event: {}", event);
        
        String eventType = (String) event.get("type");
        if ("REGISTER".equals(eventType)) {
            String userId = (String) event.get("userId");
            log.info("Processing user registration event for userId: {}", userId);
            
            try {
                UUID accountId = UUID.fromString(userId);
                accountService.createAccountSettings(accountId);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                log.error("Error processing registration event", e);
                return ResponseEntity.internalServerError().build();
            }
        }
        
        return ResponseEntity.ok().build();
    }
}