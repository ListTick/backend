package com.pro.list_tick.shopping_list.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ItemRequestDTO (

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    String name,

    @Nullable
    @Positive(message = "Value cannot be negative")
    Double value,

    @NotNull(message = "ShoppingListId cannot be null")
    UUID shoppingListId

) {
}
