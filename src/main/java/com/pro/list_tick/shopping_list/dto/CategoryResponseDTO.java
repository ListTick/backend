package com.pro.list_tick.shopping_list.dto;

import java.util.UUID;


public record CategoryResponseDTO (
    UUID id,
    String name,
    String colour
) {
}
