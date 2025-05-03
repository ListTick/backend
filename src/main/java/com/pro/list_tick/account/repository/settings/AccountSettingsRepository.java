package com.pro.list_tick.account.repository.settings;

import com.pro.list_tick.account.model.settings.AccountSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountSettingsRepository extends JpaRepository<AccountSettings, UUID> {

    @Modifying
    @Query("INSERT INTO AccountSettings(accountId) VALUES(:accountId)")
    void saveWithDefaults(UUID accountId);

    @Query("SELECT acc.defaultPomodoroDuration FROM AccountSettings acc " +
            "WHERE acc.accountId = :accountId")
    Optional<Integer> findDefaultPomodoroDuration(UUID accountId);

    @Query("SELECT acc.defaultPomodoroBreakDuration FROM AccountSettings acc " +
            "WHERE acc.accountId = :accountId")
    Optional<Integer> findDefaultPomodoroBreakDuration(UUID accountId);

    @Query("SELECT acc.defaultPomodoroLongBreakInterval FROM AccountSettings acc " +
            "WHERE acc.accountId = :accountId")
    Optional<Integer> findDefaultPomodoroLongBreakInterval(UUID accountId);

    @Query("SELECT acc.defaultPomodoroLongBreakDuration FROM AccountSettings acc " +
            "WHERE acc.accountId = :accountId")
    Optional<Integer> findDefaultPomodoroLongBreakDuration(UUID accountId);

    @Query("SELECT acc.defaultNotificationBreakReminderTime FROM AccountSettings acc " +
            "WHERE acc.accountId = :accountId")
    Optional<Integer> findDefaultNotificationBreakReminderTime(UUID accountId);

    @Query("SELECT acc.longBreakEnabled FROM AccountSettings acc " +
            "WHERE acc.accountId = :accountId")
    Optional<Boolean> findLongBreakEnabled(UUID accountId);

    @Query("SELECT acc.defaultTaskTagColour FROM AccountSettings acc " +
            "WHERE acc.accountId = :accountId")
    Optional<String> findDefaultTaskTagColour(UUID accountId);

    @Query("SELECT acc.defaultNoteTagColour FROM AccountSettings acc " +
            "WHERE acc.accountId = :accountId")
    Optional<String> findDefaultNoteTagColour(UUID accountId);

    @Query("SELECT acc.defaultShoppingListCategoryColour FROM AccountSettings acc " +
            "WHERE acc.accountId = :accountId")
    Optional<String> findDefaultShoppingListCategoryColour(UUID accountId);

    @Query("SELECT acc.defaultBucketListCategoryColour FROM AccountSettings acc " +
            "WHERE acc.accountId = :accountId")
    Optional<String> findDefaultBucketListCategoryColour(UUID accountId);

    @Query("SELECT acc.defaultGoalCategoryColour FROM AccountSettings acc " +
            "WHERE acc.accountId = :accountId")
    Optional<String> findDefaultGoalCategoryColour(UUID accountId);

}
