package com.pro.list_tick.shopping_list.dto;

import com.pro.list_tick.shopping_list.model.CurrencyCode;

import java.util.List;
import java.util.UUID;


public record ExpenseResponseDTO (
    UUID id,
    Double amount,
    CurrencyCode currency,
    Boolean reimbursed,
    UUID shoppingListId,
    List<ItemResponseDTO> items
) {
}
