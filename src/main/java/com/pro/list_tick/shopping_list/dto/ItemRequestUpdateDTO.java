package com.pro.list_tick.shopping_list.dto;

import java.util.UUID;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;

public record ItemRequestUpdateDTO(

    @Nullable
    @Size(min = 3, max = 255, message = "Name has to have 3-255 characters")
    String name,

    @Nullable
    UUID expenseId

) {
}
