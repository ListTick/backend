package com.pro.list_tick.shared;

import java.util.UUID;

public interface AccountAPI {

    UUID getAccountIdByEmail(String email);

    String getEmailByAccountId(UUID accountId);

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

}
