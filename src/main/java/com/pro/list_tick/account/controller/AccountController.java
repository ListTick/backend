package com.pro.list_tick.account.controller;

import com.pro.list_tick.account.dto.AccountSettingsCreateRequest;
import com.pro.list_tick.account.dto.AccountSettingsInputDto;
import com.pro.list_tick.account.model.settings.AccountSettings;
import com.pro.list_tick.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
@Slf4j
@Validated
public class AccountController {

    private final AccountService accountService;
    private final String requestLogTemplate = "Received request, method: {}, context path: /api/account, body {}";

    @GetMapping
    public ResponseEntity<AccountSettings> getAccountSettings() {
        log.debug(String.format(requestLogTemplate),
                "GET", "");
        final var settings = accountService.getAccountSettings();
        return ResponseEntity.ok(settings);
    }

    @PostMapping
    @Transactional(transactionManager = "accountSettingsTransactionManager")
    public ResponseEntity<Void> createAccountSetting(@Valid @RequestBody AccountSettingsCreateRequest request) {
        log.debug(String.format(requestLogTemplate),
                "POST", request);
        accountService.createAccountSettings(request.getAccountId());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional(transactionManager = "accountSettingsTransactionManager")
    public ResponseEntity<AccountSettings> updateAccountSettings(
            @Valid @RequestBody AccountSettingsInputDto accountSettingsInputDto) {
        log.debug(String.format(requestLogTemplate),
                "PUT", accountSettingsInputDto);
        final var settings = accountService.updateAccountSettings(accountSettingsInputDto);
        return ResponseEntity.ok(settings);
    }

    @PatchMapping
    @Transactional(transactionManager = "accountSettingsTransactionManager")
    public ResponseEntity<AccountSettings> updateAccountSettingsByFields(
            @RequestBody AccountSettingsInputDto accountSettingsInputDto) {
        log.debug(String.format(requestLogTemplate),
                "PATCH", accountSettingsInputDto);
        final var settings = accountService.updateAccountSettingsByFields(accountSettingsInputDto);
        return ResponseEntity.ok(settings);
    }

}
