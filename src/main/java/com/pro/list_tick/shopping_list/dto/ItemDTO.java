package com.pro.list_tick.shopping_list.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class ItemDTO {

    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @Min(value = 3, message = "Name has to have at least 3 characters long")
    @Max(value = 255, message = "Name cannot be more than 255 characters long")
    private String name;

    @Nullable
    @Positive(message = "Value cannot be negative")
    private Double value;

    @NotNull(message = "Active field cannot be null")
    private Boolean active;

    @Nullable
    private UUID expenseId;

    @NotBlank(message = "ShoppingListId cannot be blank")
    private UUID shoppingListId;

}
