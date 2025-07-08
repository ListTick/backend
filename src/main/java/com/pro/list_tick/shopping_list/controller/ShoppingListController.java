package com.pro.list_tick.shopping_list.controller;

import com.pro.list_tick.shopping_list.dto.ItemDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListInputDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListUpdateDTO;
import com.pro.list_tick.shopping_list.mapper.ShoppingListMapper;
import com.pro.list_tick.shopping_list.service.ShoppingListService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<List<ShoppingListDTO>> getAllByAccountId() {
        log.debug(String.format(requestLogTemplate),
                "GET", "", "");
        var shoppingListDTOs = shoppingListService.getAllDTOByAccountId();
        return ResponseEntity.ok(shoppingListDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingListDTO> getById(@PathVariable UUID id) {
        log.debug(String.format(requestLogTemplate),
                "GET", id, "");
        var shoppingLists = shoppingListService.getById(id);
        return ResponseEntity.ok(ShoppingListMapper.toDTO(shoppingLists));
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<ItemDTO>> getItemsByShoppingListId(@PathVariable UUID id) {
        log.debug(String.format(requestLogTemplate),
                "GET", id + "/items", "");
        var items = shoppingListService.getItemsByShoppingListId(id);
        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<ShoppingListDTO> create(@Valid @RequestBody ShoppingListInputDTO shoppingListInputDTO) {
        log.debug(String.format(requestLogTemplate),
                "POST", "", shoppingListInputDTO);
        var shoppingList = shoppingListService.create(shoppingListInputDTO);
        return ResponseEntity.status(201).body(shoppingList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingListDTO> update(@PathVariable UUID id,
                                                  @Valid @RequestBody ShoppingListUpdateDTO shoppingListUpdateDTO) {
        log.debug(String.format(requestLogTemplate),
                "PUT", id, shoppingListUpdateDTO);
        var shoppingList = shoppingListService.update(id, shoppingListUpdateDTO);
        return ResponseEntity.ok(shoppingList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ShoppingListDTO> updateByFields(@PathVariable UUID id,
                                                          @RequestBody ShoppingListUpdateDTO shoppingListUpdateDTO) {
        log.debug(String.format(requestLogTemplate),
                "PATCH", id, shoppingListUpdateDTO);
        var shoppingList = shoppingListService.updateByFields(id, shoppingListUpdateDTO);
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
