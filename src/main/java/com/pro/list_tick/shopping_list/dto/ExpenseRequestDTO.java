package com.pro.list_tick.shopping_list.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.pro.list_tick.shopping_list.model.CurrencyCode;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ExpenseRequestDTO (

    @NotNull(message = "Amount cannot be empty")
    @Positive(message = "Amount cannot be negative")
    BigDecimal amount,

    @Enumerated(EnumType.STRING)
    CurrencyCode currency,

    @NotNull(message = "Reimbursed cannot be empty")
    Boolean reimbursed,

    @NotNull(message = "Shopping list id cannot be empty")
    UUID shoppingListId,

    @Nullable
    List<UUID> items

) {
}
