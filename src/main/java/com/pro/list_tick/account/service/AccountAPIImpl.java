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
    public UUID getAccountIdByEmail(String email) {
        return keycloakRepository.findIdByEmail(email)
                .orElseThrow(() -> new AccountException("Account not found: " + email));
    }

    @Override
    public String getDefaultPomodoroDurationById(UUID accountId) {
        return "";
    }

    @Override
    public String getDefaultPomodoroBreakDurationById(UUID accountId) {
        return "";
    }

    @Override
    public String getDefaultPomodoroLongBreakDurationById(UUID accountId) {
        return "";
    }

    @Override
    public String getDefaultPomodoroLongBreakIntervalById(UUID accountId) {
        return "";
    }

    @Override
    public String getDefaultNotificationBreakReminderTimeById(UUID accountId) {
        return "";
    }

    @Override
    public String getLongBreakEnabledById(UUID accountId) {
        return "";
    }

    @Override
    public String getDefaultTaskTagColourById(UUID accountId) {
        return "";
    }

    @Override
    public String getDefaultNoteTagColourById(UUID accountId) {
        return "";
    }

    @Override
    public String getDefaultGoalCategoryColourById(UUID accountId) {
        return "";
    }

    @Override
    public String getDefaultShoppingListCategoryColourById(UUID accountId) {
        return "";
    }

    @Override
    public String getDefaultBucketListCategoryColourById(UUID accountId) {
        return "";
    }

}
