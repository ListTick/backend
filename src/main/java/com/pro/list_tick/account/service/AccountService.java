package com.pro.list_tick.account.service;

import com.pro.list_tick.account.dto.AccountSettingsInputDto;
import com.pro.list_tick.account.model.settings.AccountSettings;

import java.util.UUID;

public interface AccountService {

    UUID getUUIDbyEmail(String email);

    Integer getDefaultPomodoroDuration();

    Integer getDefaultPomodoroBreakDuration();

    Integer getDefaultPomodoroLongBreakDuration();

    Integer getDefaultPomodoroLongBreakInterval();

    Integer getDefaultNotificationBreakReminderTime();

    Boolean getLongBreakEnabled();

    String getDefaultTaskTagColour();

    String getDefaultNoteTagColour();

    String getDefaultGoalCategoryColour();

    String getDefaultShoppingListCategoryColour();

    String getDefaultBucketListCategoryColour();

    AccountSettings getAccountSettings();

    AccountSettings updateAccountSettings();

    AccountSettings updateAccountSettingsByFields(AccountSettingsInputDto accountSettingsInputDto);

}
