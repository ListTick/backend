package com.pro.list_tick.shopping_list.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ShoppingListInputDTO {

    @NotBlank(message = "Name cannot be blank")
    @Min(value = 3, message = "Name has to have at least 3 characters long")
    @Max(value = 255, message = "Name cannot be more than 255 characters long")
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
