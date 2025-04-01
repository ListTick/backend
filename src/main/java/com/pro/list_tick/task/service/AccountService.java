package com.pro.list_tick.task.service;

import com.pro.list_tick.task.model.Account;
import com.pro.list_tick.task.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("taskAccountService")
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public void createAccount(UUID accountId) {
        Account account = new Account();
        account.setId(accountId);
        accountRepository.save(account);
    }
}
