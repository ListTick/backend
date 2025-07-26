package com.pro.list_tick.bucket_list.controller;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.bucket_list.dto.ItemRequestDTO;
import com.pro.list_tick.bucket_list.dto.ItemRequestUpdateDTO;
import com.pro.list_tick.bucket_list.dto.ItemResponseDTO;
import com.pro.list_tick.bucket_list.mapper.ItemMapper;
import com.pro.list_tick.bucket_list.service.ItemService;
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

@RestController
@RequestMapping("/api/bucket-lists/items")
@AllArgsConstructor
@Validated
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final String requestLogTemplate = "Received request, method: {}, context path: /api/bucket-lists/items{}, body {}";


    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> getById(@PathVariable UUID id) {
        log.debug(String.format(requestLogTemplate),
                "GET", id, "");
        final var item = itemService.getById(id);
        return ResponseEntity.ok(ItemMapper.toResponseDTO(item));
    }

    @GetMapping("/shopping-list/{id}")
    public ResponseEntity<List<ItemResponseDTO>> getAllByShoppingListId(@PathVariable UUID id) {
        log.debug(String.format(requestLogTemplate),
            "GET", "shopping-list " + id, "");
        final var items = itemService.getAllByBucketListId(id);
        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<ItemResponseDTO> createItem(@RequestBody ItemRequestDTO itemRequestDTO) {
        log.debug(String.format(requestLogTemplate),
                "POST", "", itemRequestDTO);
        final var item = itemService.create(itemRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> updateItem(@PathVariable UUID id,
                                                     @Valid @RequestBody ItemRequestUpdateDTO itemRequestUpdateDTO) {
        log.debug(String.format(requestLogTemplate),
                "PUT", id, itemRequestUpdateDTO);
        final var item = itemService.update(id, itemRequestUpdateDTO);
        return ResponseEntity.ok(item);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> updateItemByFields(@PathVariable UUID id,
                                                             @RequestBody ItemRequestUpdateDTO itemRequestUpdateDTO) {
        log.debug(String.format(requestLogTemplate),
                "PATCH", id, itemRequestUpdateDTO);
        final var item = itemService.updateByFields(id, itemRequestUpdateDTO);
        return ResponseEntity.ok(item);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateItem(@PathVariable UUID id) {
        log.debug(String.format(requestLogTemplate),
            "PATCH", id + "/deactivate", "");
        itemService.deactivate(id);
        return ResponseEntity.ok().build();
    }

}
