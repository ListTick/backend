package com.pro.list_tick.shopping_list.dto;

import java.time.LocalDate;
import java.util.UUID;


public record ShoppingListResponseDTO (
    UUID id,
    String name,
    Boolean active,
    Boolean shared,
    LocalDate creationDate,
    CategoryResponseDTO category,
    UUID accountId
) {
}
