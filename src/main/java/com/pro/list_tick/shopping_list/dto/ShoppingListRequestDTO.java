package com.pro.list_tick.shopping_list.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record ShoppingListRequestDTO(

    @NotBlank(message = "'name' cannot be blank")
    @Size(min = 3, max = 255, message = "'name' has to have between 3 and 255 characters")
    String name,

    @Nullable
    Boolean active,

    @NotNull(message = "'categoryId' cannot be null")
    UUID categoryId,

    @NotNull(message = "'shared' cannot be null")
    Boolean shared,

    @Nullable
    List<AccountSharedWithRequestDto> sharedWithAccounts

) {
}
