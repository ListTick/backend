package com.pro.list_tick.shopping_list.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.pro.list_tick.shopping_list.dto.ExpenseRequestDTO;
import com.pro.list_tick.shopping_list.dto.ExpenseResponseDTO;
import com.pro.list_tick.shopping_list.dto.ExpenseShareResponseDto;
import com.pro.list_tick.shopping_list.model.Expense;
import com.pro.list_tick.shopping_list.model.ExpenseShare;

public class ExpenseMapper {

  private ExpenseMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static Expense toModel(ExpenseRequestDTO expenseRequestDTO) {
    Expense expense = new Expense();
    expense.setAmount(expenseRequestDTO.amount());
    expense.setCurrency(expenseRequestDTO.currency());
    expense.setReimbursed(expenseRequestDTO.reimbursed());
    expense.setCreationDate(LocalDate.now());
    expense.setItems(new ArrayList<>());
    return expense;
  }

  public static ExpenseResponseDTO toResponseDto(Expense expense) {
    return new ExpenseResponseDTO(
        expense.getId(),
        expense.getAmount(),
        expense.getCurrency(),
        expense.getReimbursed(),
        expense.getShared(),
        expense.getCreationDate(),
        expense.getShoppingList().getId(),
        expense.getItems().stream()
            .map(ItemMapper::toResponseDTO)
            .collect(Collectors.toList())
    );
  }

  public static ExpenseShareResponseDto toResponseDto(ExpenseShare expenseShare) {
    return new ExpenseShareResponseDto(
        expenseShare.getId(),
        expenseShare.getAmount(),
        expenseShare.getCurrency(),
        expenseShare.getReimbursed(),
        expenseShare.getCreationDate(),
        expenseShare.getAccountId(),
        expenseShare.getExpense().getId()
    );
  }

}
