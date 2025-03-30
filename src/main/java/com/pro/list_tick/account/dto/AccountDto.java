package com.pro.list_tick.account.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AccountDto(

    @NotBlank(message = "Username cannot be blank")
    String username,

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    String email,

    @NotBlank(message = "Password cannot be blank")
    String password
) {
}
