package com.pro.list_tick.shopping_list.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ShoppingListDTO {

    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @Min(value = 3, message = "Name has to have at least 3 characters long")
    @Max(value = 255, message = "Name cannot be more than 255 characters long")
    private String name;

    @NotNull(message = "Active status cannot be null")
    private Boolean active;

    @PastOrPresent(message = "Creation date cannot be in the future")
    private LocalDate creationDate;

    @NotNull
    private UUID categoryId;

    @NotNull
    private UUID accountId;

}
