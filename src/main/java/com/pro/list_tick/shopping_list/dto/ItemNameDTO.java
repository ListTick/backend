package com.pro.list_tick.shopping_list.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class ItemNameDTO {

    @NotBlank(message = "Id cannot be blank")
    private UUID id;

    @Nullable
    @Size(min = 3, max = 255, message = "Name has to have 3-255 characters")
    private String name;

    @Nullable
    @Positive(message = "Value cannot be negative")
    private Double value;

}
