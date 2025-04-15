package com.pro.list_tick.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TagRequestDto(

        @NotBlank(message = "Name cannot be blank")
        @Size(min = 1, max = 255, message = "Name should be between 1 and 255 characters")
        String name,

        @Pattern(
                regexp = "^#[0-9A-Fa-f]{6}$",
                message = "Color must be a valid hex color code (e.g., #FFFFFF)"
        )
        String color
) {
}
