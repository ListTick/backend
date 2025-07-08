package com.pro.list_tick.shopping_list.service;

import com.pro.list_tick.shopping_list.dto.ExpenseDTO;
import com.pro.list_tick.shopping_list.model.Expense;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    Expense getById(UUID id);
    List<ExpenseDTO> getAllByAccountId();
    ExpenseDTO create(ExpenseDTO expenseDTO);
    ExpenseDTO update(UUID id, ExpenseDTO expenseDTO);
    ExpenseDTO updateByFields(UUID id, ExpenseDTO expenseDTO);
    void delete(UUID id);

}
