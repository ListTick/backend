package com.pro.list_tick.shopping_list.service.implementation;

import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.shopping_list.dto.ExpenseDTO;
import com.pro.list_tick.shopping_list.exception.ExpenseException;
import com.pro.list_tick.shopping_list.exception.ItemException;
import com.pro.list_tick.shopping_list.mapper.ExpenseMapper;
import com.pro.list_tick.shopping_list.model.Expense;
import com.pro.list_tick.shopping_list.model.Item;
import com.pro.list_tick.shopping_list.repository.ExpenseRepository;
import com.pro.list_tick.shopping_list.service.ExpenseService;
import com.pro.list_tick.shopping_list.service.ItemService;
import com.pro.list_tick.shopping_list.service.ShoppingListService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    public Expense getById(UUID id) {
        log.debug("Getting the expense: {}", id);
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new ExpenseException(HttpStatus.NOT_FOUND,
                String.format("Couldn't find the expense: %s", id)));
        validateExpenseAccess(expense);

        return expense;
    }

    public List<ExpenseDTO> getAllByAccountId() {
        var accountId = accountService.getCurrentAccountId();
        log.debug("Getting all expenses for the account id: {}", accountId);

        return expenseRepository.findAllByAccountId(accountId)
            .stream()
            .map(ExpenseMapper::toDto)
            .toList();
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ExpenseDTO create(ExpenseDTO expenseDTO) {
        log.debug("Creating the expense: {}", expenseDTO.getAmount());
        Expense expense = ExpenseMapper.toModel(expenseDTO);
        var shoppingList = shoppingListService.getById(expenseDTO.getShoppingListId());
        expense.setShoppingList(shoppingList);

        var itemNameDTOS = Optional.ofNullable(expenseDTO.getItems())
            .orElse(Collections.emptyList());
        itemNameDTOS.forEach(itemNameDTO -> {
            Item item = itemService.getById(itemNameDTO.getId());
            if (shoppingList.getId().equals(item.getShoppingList().getId())) {
                expense.getItems().add(item);
            } else {
                throw new ItemException(HttpStatus.BAD_REQUEST, "Items have to be assign to one shopping list");
            }
        });

        var savedExpense = expenseRepository.save(expense);
        log.info("The expense has been created: {}", savedExpense.getId());
        return ExpenseMapper.toDtoWithItems(savedExpense);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ExpenseDTO update(UUID id, ExpenseDTO expenseDTO) {
        log.debug("Updating expense: {}", id);
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new ExpenseException(HttpStatus.NOT_FOUND,
                String.format("Couldn't find the expense: %s", id)));
        validateExpenseAccess(expense);

        expense.setCurrency(expenseDTO.getCurrency());
        expense.setAmount(expenseDTO.getAmount());
        expense.setReimbursed(expenseDTO.getReimbursed());

        var savedExpense = expenseRepository.save(expense);
        log.info("The expense has been updated: {}", savedExpense.getId());
        return ExpenseMapper.toDto(savedExpense);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ExpenseDTO updateByFields(UUID id, ExpenseDTO expenseDTO) {
        log.debug("Updating expense by fields: {}", id);
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new ExpenseException(HttpStatus.NOT_FOUND,
                String.format("Couldn't find the expense: %s", id)));
        validateExpenseAccess(expense);

      if (Objects.nonNull(expenseDTO.getCurrency())) {
        expense.setCurrency(expenseDTO.getCurrency());
      }
      if (Objects.nonNull(expenseDTO.getAmount())) {
        expense.setAmount(expenseDTO.getAmount());
      }
      if (Objects.nonNull(expenseDTO.getReimbursed())) {
        expense.setReimbursed(expenseDTO.getReimbursed());
      }

      var savedExpense = expenseRepository.save(expense);
      log.info("The expense has been updated by fields: {}", savedExpense.getId());
      return ExpenseMapper.toDto(savedExpense);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public void delete(UUID id) {
        log.debug("Deleting the expense: {}", id);
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new ExpenseException(HttpStatus.NOT_FOUND,
                String.format("Couldn't find the expense: %s", id)));
        validateExpenseAccess(expense);
        expenseRepository.delete(expense);
        log.info("The expense has been deleted: {}", expense);
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
