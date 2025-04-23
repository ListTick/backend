package com.pro.list_tick.shopping_list.controller;

import com.pro.list_tick.shopping_list.dto.ItemDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListInputDTO;
import com.pro.list_tick.shopping_list.service.ShoppingListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @GetMapping
    public ResponseEntity<List<ShoppingListDTO>> getAllByAccountId() {
        var shoppingLists = shoppingListService.getAllByAccountId();
        return ResponseEntity.ok(shoppingLists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingListDTO> getById(@PathVariable UUID id) {
        var shoppingLists = shoppingListService.getById(id);
        return ResponseEntity.ok(shoppingLists);
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<ItemDTO>> getItemsByShoppingListId(@PathVariable UUID id) {
        var items = shoppingListService.getItemsByShoppingListId(id);
        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<ShoppingListDTO> create(@Valid @RequestBody ShoppingListInputDTO shoppingListInputDTO) {
        var shoppingList = shoppingListService.create(shoppingListInputDTO);
        return ResponseEntity.status(201).body(shoppingList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingListDTO> update(@PathVariable UUID id,
                                                  @Valid @RequestBody ShoppingListDTO shoppingListDTO) {
        var shoppingList = shoppingListService.update(id, shoppingListDTO);
        return ResponseEntity.ok(shoppingList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ShoppingListDTO> updateByFields(@PathVariable UUID id,
                                                          @RequestBody ShoppingListDTO shoppingListDTO) {
        var shoppingList = shoppingListService.updateByFields(id, shoppingListDTO);
        return ResponseEntity.ok(shoppingList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        shoppingListService.delete(id);
        return ResponseEntity.status(204).build();
    }

}
