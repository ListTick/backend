package com.pro.list_tick.shopping_list.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ItemException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ItemException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
