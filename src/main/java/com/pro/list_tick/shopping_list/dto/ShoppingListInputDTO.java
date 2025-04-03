package com.pro.list_tick.shopping_list.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ShoppingListInputDTO {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;

    @NotNull
    private UUID categoryId;

    @NotNull
    private UUID accountId;

    @NotNull
    @JsonProperty
    private boolean isShared;

    @Nullable
    private List<AccountSharedWithDto> sharedWithAccounts;

}
