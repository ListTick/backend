package com.pro.list_tick.shopping_list.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryDTO {

    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;

    @NotBlank(message = "Colour cannot be blank")
    @Size(min = 7, max = 7, message = "'colour must be exactly 7 characters long")
    private String colour;

}
