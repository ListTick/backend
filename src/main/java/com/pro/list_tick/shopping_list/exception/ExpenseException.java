package com.pro.list_tick.shopping_list.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExpenseException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ExpenseException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
