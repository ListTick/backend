package com.pro.list_tick.account.exception;


import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString(callSuper = true)
public class AccountException extends RuntimeException {

    private final HttpStatus status;

    public AccountException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public AccountException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

}
