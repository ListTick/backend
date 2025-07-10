package com.pro.list_tick.shopping_list.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ShoppingListRequestUpdateDTO (

    @NotBlank(message = "'name' cannot be blank")
    @Size(min = 3, max = 255, message = "'name' has to have between 3 and 255 characters")
    String name,

    @NotNull(message = "'active' cannot be null")
    Boolean active,

    @NotNull(message = "'categoryId' cannot be null")
    UUID categoryId

) {
}
