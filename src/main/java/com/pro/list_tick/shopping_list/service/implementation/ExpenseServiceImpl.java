package com.pro.list_tick.shopping_list.service.implementation;

import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.shopping_list.dto.ExpenseDTO;
import com.pro.list_tick.shopping_list.repository.ExpenseRepository;
import com.pro.list_tick.shopping_list.service.ExpenseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
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

        return expenseRepository.findAllByAccountId(accountId);
    }

    public ExpenseDTO getById(UUID id) {
        return null;
    }

    public ExpenseDTO create(ExpenseDTO expenseDTO) {
        return null;
    }

    public ExpenseDTO update(ExpenseDTO expenseDTO) {
        return null;
    }

    public ExpenseDTO updateByFields(ExpenseDTO expenseDTO) {
        return null;
    }

    public void delete(UUID id) {

    }

}
