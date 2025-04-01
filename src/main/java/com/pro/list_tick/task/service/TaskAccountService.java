package com.pro.list_tick.task.service;

import com.pro.list_tick.task.model.Account;
import com.pro.list_tick.task.repository.TaskAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskAccountService {
    private final TaskAccountRepository taskAccountRepository;

    public void createAccount(UUID accountId) {
        Account account = new Account();
        account.setId(accountId);
        taskAccountRepository.save(account);
    }
}
