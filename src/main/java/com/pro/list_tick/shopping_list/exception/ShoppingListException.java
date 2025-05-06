package com.pro.list_tick.shopping_list.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString(callSuper = true)
public class ShoppingListException extends RuntimeException{

    private final HttpStatus status;

    public ShoppingListException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ShoppingListException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

}
