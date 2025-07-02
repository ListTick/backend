package com.pro.list_tick.shopping_list.mapper;

import java.util.Objects;

import com.pro.list_tick.shopping_list.dto.ExpenseDTO;
import com.pro.list_tick.shopping_list.model.Expense;

public class ExpenseMapper {

  private ExpenseMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static Expense toModel(ExpenseDTO expenseDTO) {
    Expense expense = new Expense();
    expense.setId(expenseDTO.getId());
    expense.setAmount(expenseDTO.getAmount());
    expense.setCurrency(expenseDTO.getCurrency());
    expense.setReimbursed(expenseDTO.getReimbursed());
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
    return expenseDTO;
  }

}
