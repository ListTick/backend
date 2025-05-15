package com.pro.list_tick.shopping_list.controller;

import com.pro.list_tick.shopping_list.dto.ItemDTO;
import com.pro.list_tick.shopping_list.service.ItemService;
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
@RequestMapping("/api/items")
@AllArgsConstructor
@Validated
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final String requestLogTemplate = "Received request, method: {}, context path: /api/items{}, body {}";

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getAllByAccountId() {
        log.debug(String.format(requestLogTemplate),
                "GET", "", "");
        final var items = itemService.getAllByAccountId();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getById(@PathVariable UUID id) {
        log.debug(String.format(requestLogTemplate),
                "GET", id, "");
        final var item = itemService.getById(id);
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO itemDTO) {
        log.debug(String.format(requestLogTemplate),
                "POST", "", itemDTO);
        final var item = itemService.create(itemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PutMapping
    public ResponseEntity<ItemDTO> updateItem(@Valid @RequestBody ItemDTO itemDTO) {
        log.debug(String.format(requestLogTemplate),
                "PUT", "", itemDTO);
        final var item = itemService.update(itemDTO);
        return ResponseEntity.ok(item);
    }

    @PatchMapping
    public ResponseEntity<ItemDTO> updateItemByFields(@RequestBody ItemDTO itemDTO) {
        log.debug(String.format(requestLogTemplate),
                "PATCH", "", itemDTO);
        final var item = itemService.update(itemDTO);
        return ResponseEntity.ok(item);
    }

}
