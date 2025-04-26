package com.pro.list_tick.shopping_list.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryInputDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Nullable
    private String colour;

}
