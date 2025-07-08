package com.pro.list_tick.shopping_list.controller;

import com.pro.list_tick.shopping_list.dto.ExpenseDTO;
import com.pro.list_tick.shopping_list.mapper.ExpenseMapper;
import com.pro.list_tick.shopping_list.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
@AllArgsConstructor
@Validated
@Slf4j
public class ExpenseController {

    private final ExpenseService expenseService;
    private final String requestLogTemplate = "Received request, method: {}, context path: /api/expense{}, body {}";

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getAllByAccountId() {
        log.debug(String.format(requestLogTemplate),
                "GET", "", "");
        final var expenses = expenseService.getAllByAccountId();
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> getById(@PathVariable UUID id) {
        log.debug(String.format(requestLogTemplate),
                "GET", id, "");
        final var expense = expenseService.getById(id);
        return ResponseEntity.ok(ExpenseMapper.toDto(expense));
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDTO) {
        log.debug(String.format(requestLogTemplate),
                "POST", "", expenseDTO);
        final var expense = expenseService.create(expenseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> updateExpense(@PathVariable UUID id,
                                                    @Valid @RequestBody ExpenseDTO expenseDTO) {
        log.debug(String.format(requestLogTemplate),
                "PUT", "", expenseDTO);
        final var expense = expenseService.update(id, expenseDTO);
        return ResponseEntity.ok(expense);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExpenseDTO> updateExpenseByFields(@PathVariable UUID id,
                                                            @RequestBody ExpenseDTO expenseDTO) {
        log.debug(String.format(requestLogTemplate),
                "PATCH", "", expenseDTO);
        final var item = expenseService.update(id, expenseDTO);
        return ResponseEntity.ok(item);
    }

}
