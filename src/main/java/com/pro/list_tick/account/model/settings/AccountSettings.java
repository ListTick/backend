package com.pro.list_tick.account.model.settings;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Entity
@Data
@Table(name = "account_settings")
public class AccountSettings {

    @Id
    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "default_pomodoro_duration")
    @ColumnDefault("25")
    private Integer defaultPomodoroDuration;

    @Column(name = "default_pomodoro_break_duration")
    @ColumnDefault("5")
    private Integer defaultPomodoroBreakDuration;

    @Column(name = "default_pomodoro_long_break_interval")
    @ColumnDefault("4")
    private Integer defaultPomodoroLongBreakInterval;

    @Column(name = "default_pomodoro_long_break_duration")
    @ColumnDefault("15")
    private Integer defaultPomodoroLongBreakDuration;

    @Column(name = "default_notification_break_reminder_time")
    @ColumnDefault("3")
    private Integer defaultNotificationBreakReminderTime;

    @Column(name = "long_break_enabled")
    @ColumnDefault("true")
    private Boolean longBreakEnabled;

    @Column(name = "default_task_tag_colour", length = 7)
    @ColumnDefault("#494d50")
    private String defaultTaskTagColour;

    @Column(name = "default_note_tag_colour", length = 7)
    @ColumnDefault("#494d50")
    private String defaultNoteTagColour;

    @Column(name = "default_shopping_list_category_colour", length = 7)
    @ColumnDefault("#494d50")
    private String defaultShoppingListCategoryColour;

    @Column(name = "default_bucket_list_category_colour", length = 7)
    @ColumnDefault("#494d50")
    private String defaultBucketListCategoryColour;

    @Column(name = "default_goal_category_colour", length = 7)
    @ColumnDefault("#494d50")
    private String defaultGoalCategoryColour;

}
