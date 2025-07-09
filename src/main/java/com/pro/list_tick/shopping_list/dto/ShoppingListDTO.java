package com.pro.list_tick.shopping_list.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ShoppingListDTO {

    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;

    @NotNull(message = "Active status cannot be null")
    private Boolean active;

    @NotNull(message = "Shared status cannot be null")
    private Boolean shared;

    @PastOrPresent(message = "Creation date cannot be in the future")
    private LocalDate creationDate;

    @NotNull(message = "CategoryId cannot be null")
    private UUID categoryId;

    @NotNull(message = "AccountId cannot be null")
    private UUID accountId;

}
