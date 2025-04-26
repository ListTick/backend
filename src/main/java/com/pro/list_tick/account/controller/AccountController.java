package com.pro.list_tick.account.controller;

import com.pro.list_tick.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{email}/id")
    public ResponseEntity<UUID> getUUIDbyEmail(@PathVariable String email) {
        final UUID uuid = accountService.getUUIDbyEmail(email);
        return ResponseEntity.ok(uuid);
    }

}
