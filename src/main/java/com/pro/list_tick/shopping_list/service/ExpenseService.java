package com.pro.list_tick.shopping_list.service;

import com.pro.list_tick.shopping_list.dto.ExpenseRequestDTO;
import com.pro.list_tick.shopping_list.dto.ExpenseRequestUpdateDTO;
import com.pro.list_tick.shopping_list.dto.ExpenseResponseDTO;
import com.pro.list_tick.shopping_list.dto.ExpenseShareResponseDto;
import com.pro.list_tick.shopping_list.model.Expense;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    Expense getById(UUID id);
    Expense getBySharedExpenseId(UUID id);
    List<ExpenseResponseDTO> getAllByAccountId();
    List<ExpenseShareResponseDto> getAllSharedByAccountId(String status);
    ExpenseResponseDTO create(ExpenseRequestDTO expenseRequestDTO);
    ExpenseResponseDTO update(UUID id, ExpenseRequestUpdateDTO expenseRequestUpdateDTO);
    ExpenseResponseDTO updateByFields(UUID id, ExpenseRequestUpdateDTO expenseRequestUpdateDTO);
    void delete(UUID id);
    ExpenseResponseDTO reimburse(UUID id);
    ExpenseShareResponseDto reimburseShared(UUID id);

}
