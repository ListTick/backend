package com.pro.list_tick.bucket_list.exception;


import org.springframework.http.HttpStatus;

public record ErrorEntity(HttpStatus status, String message) {

}
