package com.pro.list_tick.note.exception;


import org.springframework.http.HttpStatus;

public record ErrorEntity(HttpStatus status, String message) {

}
