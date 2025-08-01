package com.pro.list_tick.shopping_list.service;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.shopping_list.dto.ExpenseShareResponseDto;
import com.pro.list_tick.shopping_list.model.Expense;
import com.pro.list_tick.shopping_list.model.ExpenseShare;
import com.pro.list_tick.shopping_list.model.ShoppingList;

public interface ExpenseShareService {

  ExpenseShare getById(UUID id);
  List<ExpenseShare> createExpenseShares(Expense expense, ShoppingList shoppingList, UUID accountId);
  List<ExpenseShare> findAllExpenseSharesByAccountId(UUID accountId);
  List<ExpenseShare> findAllExpenseSharesByAccountIdAndReimbursed(UUID accountId, Boolean reimbursed);
  ExpenseShareResponseDto reimburse(UUID id);

}
