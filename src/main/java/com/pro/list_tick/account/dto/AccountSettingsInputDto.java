package com.pro.list_tick.account.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountSettingsInputDto {

    @NotNull(message = "'defaultPomodoroDuration' cannot be null")
    private Integer defaultPomodoroDuration;

    @NotNull(message = "'defaultPomodoroBreakDuration' cannot be null")
    private Integer defaultPomodoroBreakDuration;

    @NotNull(message = "'defaultPomodoroLongBreakInterval' cannot be null")
    private Integer defaultPomodoroLongBreakInterval;

    @NotNull(message = "'defaultPomodoroLongBreakDuration' cannot be null")
    private Integer defaultPomodoroLongBreakDuration;

    @NotNull(message = "'defaultNotificationBreakReminderTime' cannot be null")
    private Integer defaultNotificationBreakReminderTime;

    @NotNull(message = "'longBreakEnabled' cannot be null")
    private Boolean longBreakEnabled;

    @NotNull(message = "'defaultTaskTagCategoryColour' cannot be null")
    @Size(min = 7, max = 7, message = "'defaultTaskTagColour' length must be 7 characters")
    private String defaultTaskTagColour;

    @NotNull(message = "'defaultNoteTagCategoryColour' cannot be null")
    @Size(min = 7, max = 7, message = "'defaultNoteTagColour' length must be 7 characters")
    private String defaultNoteTagColour;

    @NotNull(message = "'defaultShoppingListCategoryColour' cannot be null")
    @Size(min = 7, max = 7, message = "'defaultShoppingListCategoryColour' length must be 7 characters")
    private String defaultShoppingListCategoryColour;

    @NotNull(message = "'defaultBucketListCategoryColour' cannot be null")
    @Size(min = 7, max = 7, message = "'defaultBucketListCategoryColour' length must be 7 characters")
    private String defaultBucketListCategoryColour;

    @NotNull(message = "'defaultGoalCategoryColour' cannot be null")
    @Size(min = 7, max = 7, message = "'defaultGoalCategoryColour' length must be 7 characters")
    private String defaultGoalCategoryColour;

}
