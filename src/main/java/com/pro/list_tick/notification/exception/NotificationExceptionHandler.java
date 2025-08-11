package com.pro.list_tick.notification.exception;


import com.pro.list_tick.shared.AccountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.pro.list_tick.notification")
@Slf4j
public class NotificationExceptionHandler {

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ErrorEntity> handleAccountException(AccountException ex) {
        log.error(ex.toString());
        var error = new ErrorEntity(ex.getHttpStatus(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(error);
    }

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<ErrorEntity> handleNoteException(NotificationException ex) {
        log.error(ex.toString());
        var error = new ErrorEntity(ex.getHttpStatus(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorEntity> handleValidationException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .reduce((msg1, msg2) -> msg1 + "; " + msg2)
            .orElse("Validation failed");
        var error = new ErrorEntity(HttpStatus.BAD_REQUEST, errorMsg);
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
