package com.pro.list_tick.account.service;

import com.pro.list_tick.account.repository.settings.AccountSettingsRepository;
import com.pro.list_tick.account.repository.keycloak.KeycloakRepository;
import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.shopping_list.exception.AccountException;
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

    public static final String ACCOUNT_NOT_FOUND = "Account not found: %s";

    private final CurrentAccountService currentAccountService;

    public UUID getUUIDbyEmail(String email) {
        return keycloakRepository.findIdByEmail(email)
                .orElseThrow(() -> new AccountException(String.format(ACCOUNT_NOT_FOUND, email)));
    }

    public Integer getDefaultPomodoroDuration() {
        final UUID accountId = currentAccountService.getCurrentAccountId();
        return accountSettingsRepository.findDefaultPomodoroDuration(accountId)
                .orElseThrow(() -> new AccountException(String.format(ACCOUNT_NOT_FOUND, accountId)));
    }

    public Integer getDefaultPomodoroBreakDuration() {
        return 0;
    }

    @Override
    public Integer getDefaultPomodoroLongBreakDuration() {
        return 0;
    }

    @Override
    public Integer getDefaultPomodoroLongBreakInterval() {
        return 0;
    }

    @Override
    public Integer getDefaultNotificationBreakReminderTime() {
        return 0;
    }

    @Override
    public Boolean getLongBreakEnabled() {
        return null;
    }

    @Override
    public String getDefaultTaskTagColour() {
        return "";
    }

    @Override
    public String getDefaultNoteTagColour() {
        return "";
    }

    @Override
    public String getDefaultGoalCategoryColour() {
        return "";
    }

    @Override
    public String getDefaultShoppingListCategoryColour() {
        return "";
    }

    @Override
    public String getDefaultBucketListCategoryColour() {
        return "";
    }

//    @Transactional
//    public void createAccount(AccountDto accountDto) {
//        AccountSettings accountSettings = accountSettingsMapper.toEntity(accountDto);
//        AccountSettings savedAccountSettings = accountSettingsRepository.save(accountSettings);
//
//        publisher.publishEvent(new AccountCreatedEvent(savedAccountSettings.getId()));
//    }

}
