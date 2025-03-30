package com.pro.list_tick.account.controller;

import com.pro.list_tick.account.dto.AccountDto;
import com.pro.list_tick.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @RequestMapping("/create")
    public ResponseEntity<String> createAccount(@Valid @RequestBody AccountDto accountDto) {
        accountService.createAccount(accountDto);

        return ResponseEntity.ok("Account created");
    }
}
