package com.pro.list_tick.shopping_list.service;

import com.pro.list_tick.shopping_list.dto.ExpenseDTO;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    List<ExpenseDTO> getAllByAccountId();
    ExpenseDTO getById(UUID id);
    ExpenseDTO create(ExpenseDTO expenseDTO);
    ExpenseDTO update(UUID id, ExpenseDTO expenseDTO);
    ExpenseDTO updateByFields(UUID id, ExpenseDTO expenseDTO);
    void delete(UUID id);

}
