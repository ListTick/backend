package com.pro.list_tick.account.service;

import com.pro.list_tick.account.dto.AccountDto;
import com.pro.list_tick.account.mapper.AccountMapper;
import com.pro.list_tick.account.model.Account;
import com.pro.list_tick.account.repository.AccountRepository;
import com.pro.list_tick.shared.event.AccountCreatedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final ApplicationEventPublisher publisher;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional
    public void createAccount(AccountDto accountDto) {
        Account account = accountMapper.toEntity(accountDto);
        Account savedAccount = accountRepository.save(account);

        publisher.publishEvent(new AccountCreatedEvent(savedAccount.getId()));
    }
}
