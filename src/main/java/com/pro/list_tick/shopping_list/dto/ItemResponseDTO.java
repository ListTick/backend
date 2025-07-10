package com.pro.list_tick.shopping_list.dto;

import java.util.UUID;

public record ItemResponseDTO (
    UUID id,
    String name,
    Double value
) {
}
