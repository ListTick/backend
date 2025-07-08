package com.pro.list_tick.shopping_list.mapper;

import java.util.ArrayList;
import java.util.Objects;

import com.pro.list_tick.shopping_list.dto.ExpenseDTO;
import com.pro.list_tick.shopping_list.model.Expense;

public class ExpenseMapper {

  private ExpenseMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static Expense toModel(ExpenseDTO expenseDTO) {
    Expense expense = new Expense();
    if (Objects.nonNull(expense.getId())) {
      expense.setId(expenseDTO.getId());
    }
    expense.setAmount(expenseDTO.getAmount());
    expense.setCurrency(expenseDTO.getCurrency());
    expense.setReimbursed(expenseDTO.getReimbursed());
    expense.setItems(new ArrayList<>());
    return expense;
  }

  public static ExpenseDTO toDto(Expense expense) {
    ExpenseDTO expenseDTO = new ExpenseDTO();
    if (Objects.nonNull(expense.getId())) {
      expenseDTO.setId(expense.getId());
    }
    expenseDTO.setAmount(expense.getAmount());
    expenseDTO.setCurrency(expense.getCurrency());
    expenseDTO.setReimbursed(expense.getReimbursed());
    expenseDTO.setShoppingListId(expense.getShoppingList().getId());
    return expenseDTO;
  }

  public static ExpenseDTO toDtoWithItems(Expense expense) {
    ExpenseDTO expenseDTO = toDto(expense);
    if (Objects.nonNull(expense.getItems()) &&
        !expense.getItems().isEmpty()) {
      var itemDTOs = expense.getItems()
          .stream()
          .map(ItemMapper::toItemNameDto)
          .toList();
      expenseDTO.setItems(itemDTOs);
    }
    return expenseDTO;
  }

}
