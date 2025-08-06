package com.pro.list_tick.task.dto;

import java.util.UUID;

public record TagResponseDto(
        UUID id,
        String name,
        String color
) {
}
