package com.pro.list_tick.bucket_list.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString(callSuper = true)
public class BucketListException extends RuntimeException{

    private final HttpStatus status;

    public BucketListException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public BucketListException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

}
