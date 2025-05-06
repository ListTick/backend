package com.pro.list_tick.account.exception;

import org.springframework.http.HttpStatus;

public record ErrorEntity(HttpStatus status, String message) {
}
