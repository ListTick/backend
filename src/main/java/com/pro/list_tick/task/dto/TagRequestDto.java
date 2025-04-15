package com.pro.list_tick.task.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record TagRequestDto(

        @NotBlank(message = "Name cannot be blank")
        @Max(value = 255, message = "Name should be less than 255 characters")
        String name,

        @Pattern(
                regexp = "^#[0-9A-Fa-f]{6}$",
                message = "Color must be a valid hex color code (e.g., #FFFFFF)"
        )
        String color
) {
}
