package com.pro.list_tick.account.controller;

import com.pro.list_tick.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @RequestMapping("/create")
    public ResponseEntity<String> createAccount() {
        accountService.createAccount();
        System.out.println("Account created");

        return ResponseEntity.ok("Account created");
    }
}
