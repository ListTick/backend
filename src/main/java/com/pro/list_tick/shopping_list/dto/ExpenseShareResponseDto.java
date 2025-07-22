package com.pro.list_tick.shopping_list.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.pro.list_tick.shopping_list.model.CurrencyCode;

public record ExpenseShareResponseDto (
    UUID id,
    BigDecimal amount,
    CurrencyCode currency,
    Boolean reimbursed,
    UUID accountId,
    UUID expenseId
){
}
