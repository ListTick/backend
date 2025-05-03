package com.pro.list_tick.account.service;

import com.pro.list_tick.shared.api.AccountAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountAPIImpl implements AccountAPI {

    private final AccountService accountService;

    @Override
    public UUID getAccountIdByEmail(String email) {
        return accountService.getUUIDbyEmail(email);
    }

    @Override
    public Integer getDefaultPomodoroDuration() {
        return accountService.getDefaultPomodoroDuration();
    }

    @Override
    public Integer getDefaultPomodoroBreakDuration() {
        return accountService.getDefaultPomodoroBreakDuration();
    }

    @Override
    public Integer getDefaultPomodoroLongBreakDuration() {
        return accountService.getDefaultPomodoroLongBreakDuration();
    }

    @Override
    public Integer getDefaultPomodoroLongBreakInterval() {
        return accountService.getDefaultPomodoroLongBreakInterval();
    }

    @Override
    public Integer getDefaultNotificationBreakReminderTime() {
        return accountService.getDefaultNotificationBreakReminderTime();
    }

    @Override
    public Boolean getLongBreakEnabled() {
        return accountService.getLongBreakEnabled();
    }

    @Override
    public String getDefaultTaskTagColour() {
        return accountService.getDefaultTaskTagColour();
    }

    @Override
    public String getDefaultNoteTagColour() {
        return accountService.getDefaultNoteTagColour();
    }

    @Override
    public String getDefaultGoalCategoryColour() {
        return accountService.getDefaultGoalCategoryColour();
    }

    @Override
    public String getDefaultShoppingListCategoryColour() {
        return accountService.getDefaultShoppingListCategoryColour();
    }

    @Override
    public String getDefaultBucketListCategoryColour() {
        return accountService.getDefaultBucketListCategoryColour();
    }

}