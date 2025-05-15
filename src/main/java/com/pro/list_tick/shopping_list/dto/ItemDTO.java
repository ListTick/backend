package com.pro.list_tick.shopping_list.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class ItemDTO {

    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;

    @Nullable
    @Positive(message = "Value cannot be negative")
    private Double value;

    @Nullable
    private Boolean active;

    @Nullable
    private UUID expenseId;

    @NotBlank(message = "ShoppingListId cannot be blank")
    private UUID shoppingListId;

}
