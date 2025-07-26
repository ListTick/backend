package com.pro.list_tick.bucket_list.dto;

import jakarta.validation.constraints.Email;

public record AccountSharedWithRequestDto (

    @Email
    String email

) {
}
