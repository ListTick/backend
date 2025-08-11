package com.pro.list_tick.notification.exception;


import org.springframework.http.HttpStatus;

public record ErrorEntity(HttpStatus status, String message) {

}
