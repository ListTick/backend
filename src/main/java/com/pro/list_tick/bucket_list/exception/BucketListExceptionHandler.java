package com.pro.list_tick.bucket_list.exception;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.pro.list_tick.bucket_list")
@Slf4j
public class BucketListExceptionHandler {

    @ExceptionHandler(com.pro.list_tick.account.exception.AccountException.class)
    public ResponseEntity<ErrorEntity> handleAccountException(com.pro.list_tick.account.exception.AccountException ex) {
        log.error(ex.toString());
        var error = new ErrorEntity(ex.getHttpStatus(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(error);
    }

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ErrorEntity> handleAccountException(AccountException ex) {
        log.error(ex.toString());
        var error = new ErrorEntity(ex.getHttpStatus(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(error);
    }

    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<ErrorEntity> handleCategoryException(CategoryException ex) {
        log.error(ex.toString());
        var error = new ErrorEntity(ex.getHttpStatus(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(error);
    }

    @ExceptionHandler(BucketListException.class)
    public ResponseEntity<ErrorEntity> handleShoppingListException(BucketListException ex) {
        log.error(ex.toString());
        var error = new ErrorEntity(ex.getStatus(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorEntity> handleValidationException(MethodArgumentNotValidException ex) {
        String message = Objects.requireNonNull(ex
                        .getBindingResult()
                        .getFieldError())
                .getDefaultMessage();
        log.error(message);
        var error = new ErrorEntity(HttpStatus.BAD_REQUEST, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
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
