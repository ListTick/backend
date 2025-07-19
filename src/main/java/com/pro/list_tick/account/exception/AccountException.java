package com.pro.list_tick.account.exception;


import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString(callSuper = true)
public class AccountException extends RuntimeException {

    private final HttpStatus httpStatus;

    public AccountException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public AccountException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

}
