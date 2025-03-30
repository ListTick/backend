package com.pro.list_tick.task.listener;

import com.pro.list_tick.event.AccountCreatedEvent;
import com.pro.list_tick.task.model.Account;
import com.pro.list_tick.task.repository.TaskAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountEventListener {
    private final TaskAccountRepository taskAccountRepository;

    @TransactionalEventListener
    public void on(AccountCreatedEvent accountCreatedEvent) {
        Account account = new Account();
        account.setId(accountCreatedEvent.accountId());
        taskAccountRepository.save(account);
        log.info("Account created event received: {}", accountCreatedEvent.accountId());
    }
}
