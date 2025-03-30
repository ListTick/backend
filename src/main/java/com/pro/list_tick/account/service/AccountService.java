package com.pro.list_tick.account.service;

import com.pro.list_tick.account.dto.AccountCreatedDto;
import com.pro.list_tick.account.model.Account;
import com.pro.list_tick.account.model.Role;
import com.pro.list_tick.account.repository.AccountRepository;
import com.pro.list_tick.account.repository.RoleRepository;
import com.pro.list_tick.event.AccountCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashSet;


@Service
@RequiredArgsConstructor
public class AccountService {
    private final ApplicationEventPublisher publisher;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    public void createAccount() {
        AccountCreatedDto accountDto = saveAccount();

        publisher.publishEvent(new AccountCreatedEvent(accountDto.accountId()));
    }


    public AccountCreatedDto saveAccount() {
        Account account = new Account();
        account.setEmail("john.doe@gmail.com");
        account.setPassword("password");
        account.setUsername("john.doe");
        accountRepository.save(account);

        Role accountRole = new Role();
        accountRole.setName("ROLE_USER");
        HashSet<Account> accounts = new HashSet<>();
        accounts.add(account);
        accountRole.setAccounts(accounts);

        roleRepository.save(accountRole);

        return new AccountCreatedDto(
            account.getId(),
            account.getEmail(),
            account.getPassword(),
            account.getUsername()
        );
    }
}
