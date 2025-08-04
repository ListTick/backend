package com.pro.list_tick.shopping_list.controller;

import com.pro.list_tick.shopping_list.dto.ShoppingListResponseDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListRequestDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListRequestUpdateDTO;
import com.pro.list_tick.shopping_list.mapper.ShoppingListMapper;
import com.pro.list_tick.shopping_list.service.ShoppingListService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/shopping-lists")
@AllArgsConstructor
@Slf4j
@Validated
public class ShoppingListController {

    private final ShoppingListService shoppingListService;
    private final String requestLogTemplate = "Received request, method: {}, context path: /api/shopping-lists/{}, body {}";

    @GetMapping
    public ResponseEntity<List<ShoppingListResponseDTO>> getAllByAccountId() {
        log.debug(String.format(requestLogTemplate),
                "GET", "", "");
        var shoppingListDTOs = shoppingListService.getAllDTOByAccountId();
        return ResponseEntity.ok(shoppingListDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingListResponseDTO> getById(@PathVariable UUID id) {
        log.debug(String.format(requestLogTemplate),
                "GET", id, "");
        var shoppingLists = shoppingListService.getById(id);
        return ResponseEntity.ok(ShoppingListMapper.toResponseDTO(shoppingLists));
    }

    @PostMapping
    public ResponseEntity<ShoppingListResponseDTO> create(@Valid @RequestBody
                                                          ShoppingListRequestDTO shoppingListRequestDTO) {
        log.debug(String.format(requestLogTemplate),
                "POST", "", shoppingListRequestDTO);
        var shoppingList = shoppingListService.create(shoppingListRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingListResponseDTO> update(@PathVariable UUID id,
                                                          @Valid @RequestBody
                                                          ShoppingListRequestUpdateDTO shoppingListRequestUpdateDTO) {
        log.debug(String.format(requestLogTemplate),
                "PUT", id, shoppingListRequestUpdateDTO);
        var shoppingList = shoppingListService.update(id, shoppingListRequestUpdateDTO);
        return ResponseEntity.ok(shoppingList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ShoppingListResponseDTO> updateByFields(@PathVariable UUID id,
                                                                  @RequestBody
                                                                  ShoppingListRequestUpdateDTO shoppingListRequestUpdateDTO) {
        log.debug(String.format(requestLogTemplate),
                "PATCH", id, shoppingListRequestUpdateDTO);
        var shoppingList = shoppingListService.updateByFields(id, shoppingListRequestUpdateDTO);
        return ResponseEntity.ok(shoppingList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.debug(String.format(requestLogTemplate),
                "DELETE", id, "");
        shoppingListService.delete(id);
        return ResponseEntity.status(204).build();
    }

}
