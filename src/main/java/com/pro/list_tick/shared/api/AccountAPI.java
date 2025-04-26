package com.pro.list_tick.shared.api;

import java.util.UUID;

public interface AccountAPI {

    UUID getAccountIdByEmail(String email);

    String getDefaultPomodoroDurationById(UUID accountId);

    String getDefaultPomodoroBreakDurationById(UUID accountId);

    String getDefaultPomodoroLongBreakDurationById(UUID accountId);

    String getDefaultPomodoroLongBreakIntervalById(UUID accountId);

    String getDefaultNotificationBreakReminderTimeById(UUID accountId);

    String getLongBreakEnabledById(UUID accountId);

    String getDefaultTaskTagColourById(UUID accountId);

    String getDefaultNoteTagColourById(UUID accountId);

    String getDefaultGoalCategoryColourById(UUID accountId);

    String getDefaultShoppingListCategoryColourById(UUID accountId);

    String getDefaultBucketListCategoryColourById(UUID accountId);

}
