package com.pro.list_tick.shopping_list.mapper;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import com.pro.list_tick.shopping_list.dto.ExpenseRequestDTO;
import com.pro.list_tick.shopping_list.dto.ExpenseResponseDTO;
import com.pro.list_tick.shopping_list.model.Expense;

public class ExpenseMapper {

  private ExpenseMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static Expense toModel(ExpenseRequestDTO expenseRequestDTO) {
    Expense expense = new Expense();
    if (Objects.nonNull(expense.getId())) {
      expense.setId(expenseRequestDTO.id());
    }
    expense.setAmount(expenseRequestDTO.amount());
    expense.setCurrency(expenseRequestDTO.currency());
    expense.setReimbursed(expenseRequestDTO.reimbursed());
    expense.setItems(new ArrayList<>());
    return expense;
  }

  public static ExpenseResponseDTO toResponseDto(Expense expense) {
    return new ExpenseResponseDTO(
        expense.getId(),
        expense.getAmount(),
        expense.getCurrency(),
        expense.getReimbursed(),
        expense.getShoppingList().getId(),
        expense.getItems().stream()
            .map(ItemMapper::toResponseDTO)
            .collect(Collectors.toList())
    );
  }

}
