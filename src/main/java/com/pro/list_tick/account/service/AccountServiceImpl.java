package com.pro.list_tick.account.service;

import com.pro.list_tick.account.dto.AccountSettingsInputDto;
import com.pro.list_tick.account.exception.AccountException;
import com.pro.list_tick.account.model.settings.AccountSettings;
import com.pro.list_tick.account.repository.settings.AccountSettingsRepository;
import com.pro.list_tick.account.repository.keycloak.KeycloakRepository;
import com.pro.list_tick.shared.current_user.CurrentAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final ApplicationEventPublisher publisher;
    private final AccountSettingsRepository accountSettingsRepository;
    private final KeycloakRepository keycloakRepository;

    public static final String ACCOUNT_NOT_FOUND = "Account not found: %s";

    private final CurrentAccountService currentAccountService;

    public UUID getUUIDbyEmail(String email) {
        log.debug("Getting an user id for the email: {}", email);
        return keycloakRepository.findIdByEmail(email)
                .orElseThrow(() -> new AccountException(String.format(ACCOUNT_NOT_FOUND, email)));
    }

    public Integer getDefaultPomodoroDuration() {
        final UUID accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting a default pomodoro duration for the accountId: {}", accountId);

        return accountSettingsRepository.findDefaultPomodoroDuration(accountId)
                .orElseThrow(() -> new AccountException(String.format(ACCOUNT_NOT_FOUND, accountId)));
    }

    public Integer getDefaultPomodoroBreakDuration() {
        return 0;
    }

    public Integer getDefaultPomodoroLongBreakDuration() {
        return 0;
    }

    public Integer getDefaultPomodoroLongBreakInterval() {
        return 0;
    }

    public Integer getDefaultNotificationBreakReminderTime() {
        return 0;
    }

    public Boolean getLongBreakEnabled() {
        return null;
    }

    public String getDefaultTaskTagColour() {
        return "";
    }

    public String getDefaultNoteTagColour() {
        return "";
    }

    public String getDefaultGoalCategoryColour() {
        return "";
    }

    public String getDefaultShoppingListCategoryColour() {
        return "";
    }

    public String getDefaultBucketListCategoryColour() {
        return "";
    }

    public AccountSettings getAccountSettings() {
        return null;
    }

    public AccountSettings updateAccountSettings(AccountSettingsInputDto accountSettingsInputDto) {
        return null;
    }

    public AccountSettings updateAccountSettingsByFields(AccountSettingsInputDto accountSettingsInputDto) {
        return null;
    }

    public void createAccountSettings(String accountId) {
        log.debug("Saving default account settings for the accountId: {}", accountId);
        accountSettingsRepository.saveWithDefaults(UUID.fromString(accountId));
        log.info("Inserted new account settings for the accountId: {}", accountId);
    }

}
