package com.pro.list_tick.shopping_list.service.implementation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.pro.list_tick.shopping_list.model.Expense;
import com.pro.list_tick.shopping_list.model.ExpenseShare;
import com.pro.list_tick.shopping_list.model.ShoppingList;
import com.pro.list_tick.shopping_list.repository.ExpenseShareRepository;
import com.pro.list_tick.shopping_list.service.ExpenseShareService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ExpenseShareServiceImpl implements ExpenseShareService {

  private ExpenseShareRepository expenseShareRepository;

  public List<ExpenseShare> createExpenseShares(Expense expense, ShoppingList shoppingList) {
    var totalAmount = expense.getAmount();

    Map<UUID, Integer> accountCostFactorMap = new HashMap<>();
    accountCostFactorMap.put(shoppingList.getAccountId(), shoppingList.getOwnerCostFactor());
    shoppingList.getSharedShoppingLists().forEach(shared ->
        accountCostFactorMap.put(shared.getAccountId(), shared.getCostFactor())
    );

    List<ExpenseShare> expenseShares = new ArrayList<>();
    for (Map.Entry<UUID, Integer> entry : accountCostFactorMap.entrySet()) {
      BigDecimal shareAmount = totalAmount
          .multiply(BigDecimal.valueOf(entry.getValue()))
          .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
      ExpenseShare share = new ExpenseShare();
      share.setAmount(shareAmount);
      share.setCurrency(expense.getCurrency());
      share.setReimbursed(expense.getReimbursed());
      share.setAccountId(entry.getKey());
      share.setExpense(expense);

      var savedShare = expenseShareRepository.save(share);
      expenseShares.add(savedShare);
    }

    return expenseShares;
  }

  public List<ExpenseShare> findAllExpenseSharesByAccountId(UUID accountId) {
    return expenseShareRepository.findAllByAccountId(accountId);
  }

  public List<ExpenseShare> findAllExpenseSharesByAccountIdAndReimbursed(UUID accountId, Boolean reimbursed) {
    return expenseShareRepository.findAllByAccountIdAndReimbursed(accountId, reimbursed);
  }

}
