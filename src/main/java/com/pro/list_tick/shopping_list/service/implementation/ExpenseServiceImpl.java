package com.pro.list_tick.shopping_list.service.implementation;

import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.shopping_list.dto.ExpenseDTO;
import com.pro.list_tick.shopping_list.exception.ExpenseException;
import com.pro.list_tick.shopping_list.mapper.ExpenseMapper;
import com.pro.list_tick.shopping_list.model.Expense;
import com.pro.list_tick.shopping_list.repository.ExpenseRepository;
import com.pro.list_tick.shopping_list.service.ExpenseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CurrentAccountService currentAccountService;

    public List<ExpenseDTO> getAllByAccountId() {
        var accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting all expenses for the account id: {}", accountId);

        return expenseRepository.findAllByAccountId(accountId)
            .stream()
            .map(ExpenseMapper::toDto)
            .toList();
    }

    public ExpenseDTO getById(UUID id) {
        var accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting an expense: {}", id);
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new ExpenseException(HttpStatus.NOT_FOUND, ""));
        validateExpenseAccess(accountId, expense.getAccountId());

        return ExpenseMapper.toDto(expense);
    }

    public ExpenseDTO create(ExpenseDTO expenseDTO) {
        var accountId = currentAccountService.getCurrentAccountId();
        log.debug("Creating the expense: {}", expenseDTO.getAmount());
        Expense expense = ExpenseMapper.toModel(expenseDTO);
        validateExpenseAccess(accountId, expense.getAccountId());

        return ExpenseMapper.toDto(expenseRepository.save(expense));
    }

    public ExpenseDTO update(UUID id, ExpenseDTO expenseDTO) {
        var accountId = currentAccountService.getCurrentAccountId();
        log.debug("Updating expense: {}", id);
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new ExpenseException(HttpStatus.NOT_FOUND,
                String.format("Couldn't find the expense: %s", id)));
        validateExpenseAccess(accountId, expense.getAccountId());

        expense.setCurrency(expenseDTO.getCurrency());
        expense.setAmount(expenseDTO.getAmount());
        expense.setReimbursed(expenseDTO.getReimbursed());

        return ExpenseMapper.toDto(expenseRepository.save(expense));
    }

    public ExpenseDTO updateByFields(UUID id, ExpenseDTO expenseDTO) {
        var accountId = currentAccountService.getCurrentAccountId();
        log.debug("Updating expense: {}", id);
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new ExpenseException(HttpStatus.NOT_FOUND,
                String.format("Couldn't find the expense: %s", id)));
        validateExpenseAccess(accountId, expense.getAccountId());

      if (Objects.nonNull(expenseDTO.getCurrency())) {
        expense.setCurrency(expenseDTO.getCurrency());
      }
      if (Objects.nonNull(expenseDTO.getAmount())) {
        expense.setAmount(expenseDTO.getAmount());
      }
      if (Objects.nonNull(expenseDTO.getReimbursed())) {
        expense.setReimbursed(expenseDTO.getReimbursed());
      }

      return ExpenseMapper.toDto(expenseRepository.save(expense));
    }

    public void delete(UUID id) {
        log.debug("Deleting the expense: {}", id);
        var accountId = currentAccountService.getCurrentAccountId();
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new ExpenseException(HttpStatus.NOT_FOUND,
                String.format("Couldn't find the expense: %s", id)));
        validateExpenseAccess(accountId, expense.getAccountId());

        expenseRepository.delete(expense);
    }

    private static void validateExpenseAccess(UUID accountId, UUID expenseAccountId) {
        if (!accountId.equals(expenseAccountId)) {
            throw new ExpenseException(
                HttpStatus.FORBIDDEN,
                String.format("User doesn't have access to the expense: %s", expenseAccountId));
        }
    }

}
