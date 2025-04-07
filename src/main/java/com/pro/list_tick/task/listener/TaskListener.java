package com.pro.list_tick.task.listener;

import com.pro.list_tick.task.service.TaskAccountService;
import com.pro.list_tick.shared.event.AccountCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskListener {
    private final TaskAccountService taskAccountService;

    @TransactionalEventListener
    public void on(AccountCreatedEvent event) {
        taskAccountService.createAccount(event.accountId());
        log.info("Account created event received: {}", event.accountId());
    }
}
