package com.pro.list_tick.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record TagRequestDto(

        @NotBlank
        String name,

        @Pattern(
                regexp = "^#[0-9A-Fa-f]{6}$",
                message = "Color must be a valid hex color code (e.g., #FFFFFF)"
        )
        String color
) {
}
