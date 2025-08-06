package com.pro.list_tick.shopping_list.dto;

import java.util.UUID;

import jakarta.annotation.Nullable;

public record ItemResponseDTO (
    UUID id,
    String name,
    @Nullable
    Double value
) {
}
