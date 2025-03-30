package com.pro.list_tick.account.dto;

import java.util.UUID;

public record AccountCreatedDto(
    UUID accountId,
    String username,
    String email,
    String password
) {
}
