package com.pro.list_tick.shopping_list.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO (

    @NotBlank(message = "Name cannot be blank")
    String name,

    @Nullable
    String colour

) {
}
