package com.pro.list_tick.shopping_list.service.implementation;

import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.shopping_list.dto.ExpenseRequestDTO;
import com.pro.list_tick.shopping_list.dto.ExpenseRequestUpdateDTO;
import com.pro.list_tick.shopping_list.dto.ExpenseResponseDTO;
import com.pro.list_tick.shopping_list.dto.ExpenseShareResponseDto;
import com.pro.list_tick.shopping_list.exception.ExpenseException;
import com.pro.list_tick.shopping_list.mapper.ExpenseMapper;
import com.pro.list_tick.shopping_list.model.Expense;
import com.pro.list_tick.shopping_list.model.ExpenseShare;
import com.pro.list_tick.shopping_list.model.Item;
import com.pro.list_tick.shopping_list.repository.ExpenseRepository;
import com.pro.list_tick.shopping_list.service.ExpenseService;
import com.pro.list_tick.shopping_list.service.ExpenseShareService;
import com.pro.list_tick.shopping_list.service.ItemService;
import com.pro.list_tick.shopping_list.service.ShoppingListService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final ItemService itemService;
    private final ShoppingListService shoppingListService;
    private final CurrentAccountService accountService;
    private final ExpenseShareService expenseShareService;


    public Expense getById(UUID id) {
        log.debug("Getting the expense: {}", id);
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new ExpenseException(HttpStatus.NOT_FOUND,
                String.format("Couldn't find the expense: %s", id)));
        validateExpenseAccess(expense);

        return expense;
    }

    public List<ExpenseResponseDTO> getAllByAccountId() {
        var accountId = accountService.getCurrentAccountId();
        log.debug("Getting all expenses for the account id: {}", accountId);

        return expenseRepository.findAllByAccountIdWithItems(accountId)
            .stream()
            .map(ExpenseMapper::toResponseDto)
            .toList();
    }

    public List<ExpenseShareResponseDto> getAllSharedByAccountId(String reimbursed) {
        var accountId = accountService.getCurrentAccountId();
        log.debug("Getting all shared expenses for the account id: {}", accountId);

        List<ExpenseShare> shares;
        if (Objects.nonNull(reimbursed)) {
            shares = expenseShareService.findAllExpenseSharesByAccountIdAndReimbursed(accountId,
                Boolean.valueOf(reimbursed));
        } else {
            shares = expenseShareService.findAllExpenseSharesByAccountId(accountId);
        }

        return shares.stream().map(ExpenseMapper::toResponseDto).toList();
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ExpenseResponseDTO create(ExpenseRequestDTO expenseRequestDTO) {
        log.debug("Creating the expense: {}", expenseRequestDTO.amount());
        Expense expense = ExpenseMapper.toModel(expenseRequestDTO);
        var shoppingList = shoppingListService.getById(expenseRequestDTO.shoppingListId());
        expense.setShoppingList(shoppingList);
        var savedExpense = expenseRepository.save(expense);

        var itemIds = Optional.ofNullable(expenseRequestDTO.items())
            .orElse(Collections.emptyList());
        List<Item> items = new ArrayList<>();
        itemIds.forEach(id -> {
            var item = itemService.addExpense(id, expense);
            items.add(item);
        });
        savedExpense.setItems(items);

        if (shoppingList.getShared()) {
            var shares = expenseShareService.createExpenseShares(expense, shoppingList);
            savedExpense.setExpenseShares(shares);
        }

        log.info("The expense has been created: {}", savedExpense.getId());
        return ExpenseMapper.toResponseDto(savedExpense);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ExpenseResponseDTO update(UUID id, ExpenseRequestUpdateDTO expenseRequestUpdateDTO) {
        log.debug("Updating expense: {}", id);
        Expense expense = getById(id);

        expense.setCurrency(expenseRequestUpdateDTO.currency());
        expense.setAmount(expenseRequestUpdateDTO.amount());
        expense.setReimbursed(expenseRequestUpdateDTO.reimbursed());

        var savedExpense = expenseRepository.save(expense);
        log.info("The expense has been updated: {}", savedExpense.getId());
        return ExpenseMapper.toResponseDto(savedExpense);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ExpenseResponseDTO updateByFields(UUID id, ExpenseRequestUpdateDTO expenseRequestUpdateDTO) {
        log.debug("Updating expense by fields: {}", id);
        Expense expense = getById(id);

      if (Objects.nonNull(expenseRequestUpdateDTO.currency())) {
        expense.setCurrency(expenseRequestUpdateDTO.currency());
      }
      if (Objects.nonNull(expenseRequestUpdateDTO.amount())) {
        expense.setAmount(expenseRequestUpdateDTO.amount());
      }
      if (Objects.nonNull(expenseRequestUpdateDTO.reimbursed())) {
        expense.setReimbursed(expenseRequestUpdateDTO.reimbursed());
      }

      var savedExpense = expenseRepository.save(expense);
      log.info("The expense has been updated by fields: {}", savedExpense.getId());
      return ExpenseMapper.toResponseDto(savedExpense);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public void delete(UUID id) {
        log.debug("Deleting the expense: {}", id);
        Expense expense = getById(id);

        expenseRepository.delete(expense);
        log.info("The expense has been deleted: {}", expense);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public void reimburse(UUID id) {
        log.debug("Reimbursing the expense: {}", id);
        Expense expense = getById(id);
        expense.setReimbursed(Boolean.TRUE);

        expenseRepository.save(expense);
        log.info("The expense has been reimbursed: {}", expense);
    }

    private void validateExpenseAccess(Expense expense) {
        log.debug("Validating the expense access: {}", expense.getId());
        var accountId = accountService.getCurrentAccountId();
        var shoppingList = expense.getShoppingList();
        if (!shoppingListService.validateAccess(accountId, shoppingList) &&
            !shoppingListService.validateSharedAccess(accountId, shoppingList)) {
            throw new ExpenseException(HttpStatus.FORBIDDEN,
                String.format("User doesn't have an access to the expense: %s", expense.getId()));
        }
    }

}
