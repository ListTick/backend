package com.pro.list_tick.task.listener;

import com.pro.list_tick.event.AccountCreatedEvent;
import com.pro.list_tick.task.model.Account;
import com.pro.list_tick.task.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class AccountEventListener {
    private final AccountRepository accountRepository;

    @TransactionalEventListener
    public void on(AccountCreatedEvent accountCreatedEvent) {
        Account account = new Account();
        account.setId(accountCreatedEvent.accountId());
        accountRepository.save(account);
        System.out.println("Account created event received: " + accountCreatedEvent.accountId());
    }
}
