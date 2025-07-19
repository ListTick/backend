package com.pro.list_tick.account.service;

import com.pro.list_tick.account.dto.AccountSettingsInputDto;
import com.pro.list_tick.account.exception.AccountException;
import com.pro.list_tick.account.model.settings.AccountSettings;
import com.pro.list_tick.account.repository.settings.AccountSettingsRepository;
import com.pro.list_tick.account.repository.keycloak.KeycloakRepository;
import com.pro.list_tick.shared.current_user.CurrentAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountSettingsRepository accountSettingsRepository;
    private final KeycloakRepository keycloakRepository;

    private final CurrentAccountService currentAccountService;

    private static final String  ERROR_LOG_TEMPLATE = "{} for the accountId: {}";

    public UUID getUUIDbyEmail(String email) {
        log.debug("Getting an user id for the email: {}", email);
        String id = keycloakRepository.findIdByEmail(email)
                .orElseThrow(() -> {
                    var errorMessage = "Account not found";
                    log.error("{}  with the email: {}", errorMessage, email);
                    throw new AccountException(HttpStatus.NOT_FOUND, errorMessage);
                });
        return UUID.fromString(id);
    }

  public String getEmailByUUID(UUID accountId) {
    log.debug("Getting an email for the account id: {}", accountId);

    return keycloakRepository.findEmailById(String.valueOf(accountId))
        .orElseThrow(() -> {
          var errorMessage = "Email not found";
          log.error(ERROR_LOG_TEMPLATE, errorMessage, accountId);
          throw new AccountException(HttpStatus.NOT_FOUND, errorMessage);

    });
  }

  public Integer getDefaultPomodoroDuration() {
        final UUID accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting a default pomodoro duration for the accountId: {}", accountId);

        return accountSettingsRepository.findDefaultPomodoroDuration(accountId)
                .orElseThrow(() -> {
                    var errorMessage = "Default pomodoro duration not found";
                    log.error(ERROR_LOG_TEMPLATE, errorMessage, accountId);
                    throw new AccountException(HttpStatus.NOT_FOUND, errorMessage);
                });
    }

    public Integer getDefaultPomodoroBreakDuration() {
        final UUID accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting a default pomodoro break duration for the accountId: {}", accountId);

        return accountSettingsRepository.findDefaultPomodoroBreakDuration(accountId)
                .orElseThrow(() -> {
                    var errorMessage = "Default pomodoro break duration not found";
                    log.error(ERROR_LOG_TEMPLATE, errorMessage, accountId);
                    throw new AccountException(HttpStatus.NOT_FOUND, errorMessage);
                });
    }

    public Integer getDefaultPomodoroLongBreakDuration() {
        final UUID accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting a default pomodoro long break duration for the accountId: {}", accountId);

        return accountSettingsRepository.findDefaultPomodoroLongBreakDuration(accountId)
                .orElseThrow(() -> {
                    var errorMessage = "Default pomodoro long break duration not found";
                    log.error(ERROR_LOG_TEMPLATE, errorMessage, accountId);
                    throw new AccountException(HttpStatus.NOT_FOUND, errorMessage);
                });
    }

    public Integer getDefaultPomodoroLongBreakInterval() {
        final UUID accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting a default pomodoro long break interval for the accountId: {}", accountId);

        return accountSettingsRepository.findDefaultPomodoroLongBreakInterval(accountId)
                .orElseThrow(() -> {
                    var errorMessage = "Default pomodoro long break interval not found";
                    log.error(ERROR_LOG_TEMPLATE, errorMessage, accountId);
                    throw new AccountException(HttpStatus.NOT_FOUND, errorMessage);
                });
    }

    public Integer getDefaultNotificationBreakReminderTime() {
        final UUID accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting a default notification break reminder time for the accountId: {}", accountId);

        return accountSettingsRepository.findDefaultNotificationBreakReminderTime(accountId)
                .orElseThrow(() -> {
                    var errorMessage = "Default notification break reminder time not found";
                    log.error(ERROR_LOG_TEMPLATE, errorMessage, accountId);
                    throw new AccountException(HttpStatus.NOT_FOUND, errorMessage);
                });
    }

    public Boolean getLongBreakEnabled() {
        final UUID accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting a long break enabled for the accountId: {}", accountId);

        return accountSettingsRepository.findLongBreakEnabled(accountId)
                .orElseThrow(() -> {
                    var errorMessage = "Long break enabled not found";
                    log.error(ERROR_LOG_TEMPLATE, errorMessage, accountId);
                    throw new AccountException(HttpStatus.NOT_FOUND, errorMessage);
                });
    }

    public String getDefaultTaskTagColour() {
        final UUID accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting a default task tag colour for the accountId: {}", accountId);

        return accountSettingsRepository.findDefaultTaskTagColour(accountId)
                .orElseThrow(() -> {
                    var errorMessage = "Default task tag colour not found";
                    log.error(ERROR_LOG_TEMPLATE, errorMessage, accountId);
                    throw new AccountException(HttpStatus.NOT_FOUND, errorMessage);
                });
    }

    public String getDefaultNoteTagColour() {
        final UUID accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting a default note tag colour for the accountId: {}", accountId);

        return accountSettingsRepository.findDefaultNoteTagColour(accountId)
                .orElseThrow(() -> {
                    var errorMessage = "Default note tag colour not found";
                    log.error(ERROR_LOG_TEMPLATE, errorMessage, accountId);
                    throw new AccountException(HttpStatus.NOT_FOUND, errorMessage);
                });
    }

    public String getDefaultGoalCategoryColour() {
        final UUID accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting a default goal category colour for the accountId: {}", accountId);

        return accountSettingsRepository.findDefaultGoalCategoryColour(accountId)
                .orElseThrow(() -> {
                    var errorMessage = "Default goal category colour not found";
                    log.error(ERROR_LOG_TEMPLATE, errorMessage, accountId);
                    throw new AccountException(HttpStatus.NOT_FOUND, errorMessage);
                });
    }

    public String getDefaultShoppingListCategoryColour() {
        final UUID accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting a default shopping list category colour for the accountId: {}", accountId);

        return accountSettingsRepository.findDefaultShoppingListCategoryColour(accountId)
                .orElseThrow(() -> {
                    var errorMessage = "Default shopping list category colour not found";
                    log.error(ERROR_LOG_TEMPLATE, errorMessage, accountId);
                    throw new AccountException(HttpStatus.NOT_FOUND, errorMessage);
                });
    }

    public String getDefaultBucketListCategoryColour() {
        final UUID accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting a default bucket list category colour for the accountId: {}", accountId);

        return accountSettingsRepository.findDefaultBucketListCategoryColour(accountId)
                .orElseThrow(() -> {
                    var errorMessage = "Default bucket list category colour not found";
                    log.error(ERROR_LOG_TEMPLATE, errorMessage, accountId);
                    throw new AccountException(HttpStatus.NOT_FOUND, errorMessage);
                });
    }

    public AccountSettings getAccountSettings() {
        final UUID accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting account settings for the accountId: {}", accountId);

        return accountSettingsRepository.findById(accountId)
                .orElseThrow(() -> {
                    var errorMessage = "Account settings not found";
                    log.error(ERROR_LOG_TEMPLATE, errorMessage, accountId);
                    throw new AccountException(HttpStatus.NOT_FOUND, errorMessage);
                });
    }

    public AccountSettings updateAccountSettings(AccountSettingsInputDto accountSettingsInputDto) {
        AccountSettings accountSettings = getAccountSettings();
        log.info("Updating account settings for the accountId: {}", accountSettings.getAccountId());

        accountSettings.setDefaultPomodoroDuration(accountSettingsInputDto.getDefaultPomodoroDuration());
        accountSettings.setDefaultPomodoroBreakDuration(accountSettingsInputDto.getDefaultPomodoroBreakDuration());
        accountSettings.setDefaultPomodoroLongBreakInterval(accountSettingsInputDto.getDefaultPomodoroLongBreakInterval());
        accountSettings.setDefaultPomodoroLongBreakDuration(accountSettingsInputDto.getDefaultPomodoroLongBreakDuration());
        accountSettings.setDefaultNotificationBreakReminderTime(accountSettingsInputDto.getDefaultNotificationBreakReminderTime());
        accountSettings.setLongBreakEnabled(accountSettingsInputDto.getLongBreakEnabled());
        accountSettings.setDefaultTaskTagColour(accountSettingsInputDto.getDefaultTaskTagColour());
        accountSettings.setDefaultNoteTagColour(accountSettingsInputDto.getDefaultNoteTagColour());
        accountSettings.setDefaultShoppingListCategoryColour(accountSettingsInputDto.getDefaultShoppingListCategoryColour());
        accountSettings.setDefaultBucketListCategoryColour(accountSettingsInputDto.getDefaultBucketListCategoryColour());
        accountSettings.setDefaultGoalCategoryColour(accountSettingsInputDto.getDefaultGoalCategoryColour());

        log.debug("Saving the updated account settings for the accountId: {}", accountSettings.getAccountId());
        return accountSettingsRepository.save(accountSettings);
    }

    public AccountSettings updateAccountSettingsByFields(AccountSettingsInputDto accountSettingsInputDto) {
        AccountSettings accountSettings = getAccountSettings();
        log.info("Updating fields in the account settings for the accountId: {}", accountSettings.getAccountId());

        if (Objects.nonNull(accountSettingsInputDto.getDefaultPomodoroDuration())) {
            accountSettings.setDefaultPomodoroDuration(accountSettingsInputDto.getDefaultPomodoroDuration());
        }
        if (Objects.nonNull(accountSettingsInputDto.getDefaultPomodoroBreakDuration())) {
            accountSettings.setDefaultPomodoroBreakDuration(accountSettingsInputDto.getDefaultPomodoroBreakDuration());
        }
        if (Objects.nonNull(accountSettingsInputDto.getDefaultPomodoroLongBreakInterval())) {
            accountSettings.setDefaultPomodoroLongBreakInterval(accountSettingsInputDto.getDefaultPomodoroLongBreakInterval());
        }
        if (Objects.nonNull(accountSettingsInputDto.getDefaultPomodoroLongBreakDuration())) {
            accountSettings.setDefaultPomodoroLongBreakDuration(accountSettingsInputDto.getDefaultPomodoroLongBreakDuration());
        }
        if (Objects.nonNull(accountSettingsInputDto.getDefaultNotificationBreakReminderTime())) {
            accountSettings.setDefaultNotificationBreakReminderTime(accountSettingsInputDto.getDefaultNotificationBreakReminderTime());
        }
        if (Objects.nonNull(accountSettingsInputDto.getLongBreakEnabled())) {
            accountSettings.setLongBreakEnabled(accountSettingsInputDto.getLongBreakEnabled());
        }
        if (Objects.nonNull(accountSettingsInputDto.getDefaultTaskTagColour())) {
            accountSettings.setDefaultTaskTagColour(accountSettingsInputDto.getDefaultTaskTagColour());
        }
        if (Objects.nonNull(accountSettingsInputDto.getDefaultNoteTagColour())) {
            accountSettings.setDefaultNoteTagColour(accountSettingsInputDto.getDefaultNoteTagColour());
        }
        if (Objects.nonNull(accountSettingsInputDto.getDefaultShoppingListCategoryColour())) {
            accountSettings.setDefaultShoppingListCategoryColour(accountSettingsInputDto.getDefaultShoppingListCategoryColour());
        }
        if (Objects.nonNull(accountSettingsInputDto.getDefaultBucketListCategoryColour())) {
            accountSettings.setDefaultBucketListCategoryColour(accountSettingsInputDto.getDefaultBucketListCategoryColour());
        }
        if (Objects.nonNull(accountSettingsInputDto.getDefaultGoalCategoryColour())) {
            accountSettings.setDefaultGoalCategoryColour(accountSettingsInputDto.getDefaultGoalCategoryColour());
        }

        log.debug("Saving the updated account settings fields for the accountId: {}", accountSettings.getAccountId());
        return accountSettingsRepository.save(accountSettings);
    }

    public void createAccountSettings(String accountId) {
        log.debug("Saving default account settings for the accountId: {}", accountId);
        accountSettingsRepository.saveWithDefaults(UUID.fromString(accountId));
        log.info("Inserted new account settings for the accountId: {}", accountId);
    }

}
