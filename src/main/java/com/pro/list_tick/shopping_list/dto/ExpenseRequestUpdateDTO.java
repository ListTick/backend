package com.pro.list_tick.shopping_list.dto;

import com.pro.list_tick.shopping_list.model.CurrencyCode;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ExpenseRequestUpdateDTO(

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount cannot be negative")
    Double amount,

    @NotBlank(message = "Currency cannot be blank")
    @Size(min = 3, max = 3, message = "Currency has to be 3 characters long")
    @Enumerated(EnumType.STRING)
    CurrencyCode currency,

    @NotNull(message = "Reimbursed cannot be null")
    Boolean reimbursed

) {
}
