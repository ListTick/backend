package com.pro.list_tick.shopping_list.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.pro.list_tick.shopping_list.model.CurrencyCode;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ExpenseRequestDTO (

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount cannot be negative")
    BigDecimal amount,

    @NotBlank(message = "Currency cannot be blank")
    @Size(min = 3, max = 3, message = "Currency has to be 3 characters long")
    @Enumerated(EnumType.STRING)
    CurrencyCode currency,

    @NotNull(message = "Reimbursed cannot be null")
    Boolean reimbursed,

    @NotBlank(message = "Shopping list id cannot be blank")
    UUID shoppingListId,

    @Nullable
    List<UUID> items

) {
}
