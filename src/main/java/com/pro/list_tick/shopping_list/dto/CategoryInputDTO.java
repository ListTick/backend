package com.pro.list_tick.shopping_list.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryInputDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Colour cannot be blank")
    private String colour;

}
