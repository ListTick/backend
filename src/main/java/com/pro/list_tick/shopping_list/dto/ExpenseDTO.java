package com.pro.list_tick.shopping_list.dto;

import com.pro.list_tick.shopping_list.model.CurrencyCode;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ExpenseDTO {

    private UUID id;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount cannot be negative")
    private Double amount;

    @NotBlank(message = "Currency cannot be blank")
    @Size(min = 3, max = 3, message = "Currency has to be 3 characters long")
    @Enumerated(EnumType.STRING)
    private CurrencyCode currency;

    @NotNull(message = "Reimbursed cannot be null")
    private Boolean reimbursed;

    @Nullable
    private List<ItemNameDTO> items;

}
