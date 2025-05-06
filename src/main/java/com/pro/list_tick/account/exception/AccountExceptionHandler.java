package com.pro.list_tick.account.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AccountExceptionHandler {

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ErrorEntity> handleAccountException(AccountException ex) {
        log.error(ex.toString());
        var error = new ErrorEntity(ex.getStatus(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorEntity> handleException(Exception ex) {
        log.error(ex.getMessage());
        var error = new ErrorEntity(HttpStatus.INTERNAL_SERVER_ERROR,
                "Something went wrong, please try again later. " +
                        "If the problem repeats, please contact the application's technical support team");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
