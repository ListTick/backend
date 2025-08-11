package com.pro.list_tick.shopping_list.service.implementation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.pro.list_tick.shared.NotificationAPI;
import com.pro.list_tick.shopping_list.dto.ExpenseShareResponseDto;
import com.pro.list_tick.shopping_list.exception.ExpenseException;
import com.pro.list_tick.shopping_list.mapper.ExpenseMapper;
import com.pro.list_tick.shopping_list.model.Expense;
import com.pro.list_tick.shopping_list.model.ExpenseShare;
import com.pro.list_tick.shopping_list.model.ShoppingList;
import com.pro.list_tick.shopping_list.repository.ExpenseShareRepository;
import com.pro.list_tick.shopping_list.service.ExpenseShareService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class ExpenseShareServiceImpl implements ExpenseShareService {

  private final NotificationAPI notificationAPI;
  private ExpenseShareRepository expenseShareRepository;


  public ExpenseShare getById(UUID id) {
    log.debug("Getting the shared expense: {}", id);

    return expenseShareRepository.findById(id)
        .orElseThrow(() -> new ExpenseException(HttpStatus.NOT_FOUND,
            String.format("Couldn't find the shared expense: %s", id)));
  }

  public List<ExpenseShare> createExpenseShares(Expense expense, ShoppingList shoppingList, UUID accountId) {
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
      if (entry.getKey().equals(accountId)) {
        share.setReimbursed(Boolean.TRUE);
      } else {
        share.setReimbursed(expense.getReimbursed());
      }
      share.setCreationDate(LocalDate.now());
      share.setAccountId(entry.getKey());
      share.setExpense(expense);

      notificationAPI.create(
          expense.getId(),
          expense.getClass().getSimpleName(),
          "The shared expense has been added. Your share equals: " + share.getAmount() + " " + share.getCurrency(),
          share.getAccountId()
      );

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

  @Transactional(transactionManager = "shoppingListTransactionManager")
  public ExpenseShareResponseDto reimburse(UUID id) {
    log.debug("Reimbursing the expense: {}", id);
    ExpenseShare expenseShare = getById(id);
    expenseShare.setReimbursed(Boolean.TRUE);

    final var expense = expenseShare.getExpense();
    notificationAPI.create(
        expense.getId(),
        expense.getClass().getSimpleName(),
        "You have been reimbursed " + expenseShare.getAmount() + " " + expenseShare.getCurrency() +  " for a shared expense.",
        expense.getShoppingList().getAccountId()
    );

    var reimbursedSharedExpense = expenseShareRepository.save(expenseShare);
    log.info("The share expense has been reimbursed: {}", expenseShare);
    return ExpenseMapper.toResponseDto(reimbursedSharedExpense);
  }

}
