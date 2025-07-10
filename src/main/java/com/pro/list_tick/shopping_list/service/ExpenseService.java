package com.pro.list_tick.shopping_list.service;

import com.pro.list_tick.shopping_list.dto.ExpenseRequestDTO;
import com.pro.list_tick.shopping_list.dto.ExpenseResponseDTO;
import com.pro.list_tick.shopping_list.model.Expense;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    Expense getById(UUID id);
    List<ExpenseResponseDTO> getAllByAccountId();
    ExpenseResponseDTO create(ExpenseRequestDTO expenseRequestDTO);
    ExpenseResponseDTO update(UUID id, ExpenseRequestDTO expenseRequestDTO);
    ExpenseResponseDTO updateByFields(UUID id, ExpenseRequestDTO expenseRequestDTO);
    void delete(UUID id);

}
