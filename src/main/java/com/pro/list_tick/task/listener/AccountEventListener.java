package com.pro.list_tick.task.listener;

import com.pro.list_tick.event.AccountCreatedEvent;
import com.pro.list_tick.task.model.Account;
import com.pro.list_tick.task.repository.TaskAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountEventListener {
    private final TaskAccountRepository taskAccountRepository;

    @EventListener
    public void on(AccountCreatedEvent accountCreatedEvent) {
        Account account = new Account();
        account.setId(accountCreatedEvent.accountId());
        taskAccountRepository.save(account);
        System.out.println("Account created event received: " + accountCreatedEvent.accountId());
    }
}
