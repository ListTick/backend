package com.pro.list_tick.account.mapper;

import com.pro.list_tick.account.dto.AccountDto;
import com.pro.list_tick.account.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountDto toDto(Account account) {
        return new AccountDto(
                account.getUsername(),
                account.getEmail(),
                account.getPassword()
        );
    }

    public Account toEntity(AccountDto accountDto) {
        Account account = new Account();
        account.setUsername(accountDto.username());
        account.setEmail(accountDto.email());
        account.setPassword(accountDto.password());

        return account;
    }
}
