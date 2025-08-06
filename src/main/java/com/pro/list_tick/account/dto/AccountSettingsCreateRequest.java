package com.pro.list_tick.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountSettingsCreateRequest {

    @NotBlank(message = "Account Id must be specified")
    private String accountId;

}
