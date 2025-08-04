package com.pro.list_tick.note.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString(callSuper = true)
public class NoteException extends RuntimeException{

  private final HttpStatus httpStatus;

  public NoteException(String message) {
    super(message);
    this.httpStatus = HttpStatus.BAD_REQUEST;
  }

  public NoteException(HttpStatus httpStatus, String message) {
    super(message);
    this.httpStatus = httpStatus;
  }

}
