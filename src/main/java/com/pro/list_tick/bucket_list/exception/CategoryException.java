package com.pro.list_tick.bucket_list.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString(callSuper = true)
public class CategoryException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CategoryException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public CategoryException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
