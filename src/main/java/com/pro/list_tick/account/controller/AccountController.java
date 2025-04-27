package com.pro.list_tick.account.controller;

import com.pro.list_tick.account.model.settings.AccountSettings;
import com.pro.list_tick.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<AccountSettings> getAccountSettings() {
        final var settings = accountService.getAccountSettings();
        return ResponseEntity.ok(settings);
    }

    @PutMapping
    public ResponseEntity<AccountSettings> updateAccountSettings(@Valid @RequestBody AccountSettingsInputDto accountSettingsInputDto) {
        final var settings = accountService.updateAccountSettings();
        return ResponseEntity.ok(settings);
    }

    @PatchMapping
    public ResponseEntity<AccountSettings> updateAccountSettingsByFields(@RequestBody AccountSettingsInputDto accountSettingsInputDto) {
        final var settings = accountService.updateAccountSettingsByFields(accountSettingsInputDto);
        return ResponseEntity.ok(settings);
    }

}
