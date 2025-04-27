package com.pro.list_tick.account.mapper;

import com.pro.list_tick.account.dto.AccountSettingsInputDto;
import com.pro.list_tick.account.model.settings.AccountSettings;

public class AccountSettingsMapper {

    private AccountSettingsMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static AccountSettings toModel(AccountSettingsInputDto accountSettingsInputDto) {
        AccountSettings accountSettings = new AccountSettings();
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
        return accountSettings;
    }

}
