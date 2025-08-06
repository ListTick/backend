package com.pro.list_tick.shopping_list.controller;

import com.pro.list_tick.shopping_list.dto.ExpenseRequestDTO;
import com.pro.list_tick.shopping_list.dto.ExpenseRequestUpdateDTO;
import com.pro.list_tick.shopping_list.dto.ExpenseResponseDTO;
import com.pro.list_tick.shopping_list.dto.ExpenseShareResponseDto;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<ExpenseResponseDTO>> getAllByAccountId() {
        log.debug(String.format(requestLogTemplate),
                "GET", "", "");
        final var expenses = expenseService.getAllByAccountId();
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/shared")
    public ResponseEntity<List<ExpenseShareResponseDto>> getAllSharedByAccountId(
        @RequestParam(required = false) String reimbursed) {
        log.debug(String.format(requestLogTemplate),
            "GET", "/shared", "");
        final var shares = expenseService.getAllSharedByAccountId(reimbursed);
        return ResponseEntity.ok(shares);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> getById(@PathVariable UUID id) {
        log.debug(String.format(requestLogTemplate),
                "GET", id, "");
        final var expense = expenseService.getById(id);
        return ResponseEntity.ok(ExpenseMapper.toResponseDto(expense));
    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> createExpense(@RequestBody ExpenseRequestDTO expenseRequestDTO) {
        log.debug(String.format(requestLogTemplate),
                "POST", "", expenseRequestDTO);
        final var expense = expenseService.create(expenseRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(@PathVariable UUID id,
                                                            @Valid @RequestBody ExpenseRequestUpdateDTO expenseRequestUpdateDTO) {
        log.debug(String.format(requestLogTemplate),
                "PUT", "", expenseRequestUpdateDTO);
        final var expense = expenseService.update(id, expenseRequestUpdateDTO);
        return ResponseEntity.ok(expense);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpenseByFields(@PathVariable UUID id,
                                                                    @RequestBody ExpenseRequestUpdateDTO expenseRequestUpdateDTO) {
        log.debug(String.format(requestLogTemplate),
                "PATCH", "", expenseRequestUpdateDTO);
        final var item = expenseService.updateByFields(id, expenseRequestUpdateDTO);
        return ResponseEntity.ok(item);
    }

    @PatchMapping("/{id}/reimburse")
    public ResponseEntity<ExpenseResponseDTO> reimburse(@PathVariable UUID id) {
        log.debug(String.format(requestLogTemplate),
            "PATCH", id + "/reimburse", "");
        var reimbursedExpense = expenseService.reimburse(id);
        return ResponseEntity.ok(reimbursedExpense);
    }

    @PatchMapping("shared/{id}/reimburse")
    public ResponseEntity<ExpenseShareResponseDto> reimburseShared(@PathVariable UUID id) {
        log.debug(String.format(requestLogTemplate),
            "PATCH", "shared/" + id + "/reimburse", "");
        var reimbursedShareExpense = expenseService.reimburseShared(id);
        return ResponseEntity.ok(reimbursedShareExpense);
    }

}
