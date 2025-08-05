package com.pro.list_tick.notification.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString(callSuper = true)
public class NotificationException extends RuntimeException{

  private final HttpStatus httpStatus;

  public NotificationException(String message) {
    super(message);
    this.httpStatus = HttpStatus.BAD_REQUEST;
  }

  public NotificationException(HttpStatus httpStatus, String message) {
    super(message);
    this.httpStatus = httpStatus;
  }

}
