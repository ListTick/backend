package com.pro.list_tick.shared.current_user;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentAccountService {
    public UUID getCurrentAccountId() {
        //TODO: add implementation
        return UUID.fromString("0a247225-f9b9-4021-8848-75f56fb6fedc");
    }
}
