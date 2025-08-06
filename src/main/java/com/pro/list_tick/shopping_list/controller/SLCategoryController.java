package com.pro.list_tick.shopping_list.controller;

import com.pro.list_tick.shopping_list.dto.CategoryResponseDTO;
import com.pro.list_tick.shopping_list.dto.CategoryRequestDTO;
import com.pro.list_tick.shopping_list.mapper.CategoryMapper;
import com.pro.list_tick.shopping_list.service.SLCategoryService;
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
@RequestMapping("/api/shopping-lists/categories")
@AllArgsConstructor
@Slf4j
@Validated
public class SLCategoryController {

    private final SLCategoryService categoryService;
    private static final String REQUEST_LOG_TEMPLATE =
        "Received request, method: {}, context path: /api/shopping-lists/categories{}, body {}";

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllByAccountId() {
        log.debug(String.format(REQUEST_LOG_TEMPLATE),
                "GET", "", "");
        final var categoryDTOs = categoryService.getAllDTOByAccountId();
        return ResponseEntity.ok(categoryDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable UUID id) {
        log.debug(String.format(REQUEST_LOG_TEMPLATE),
                "GET", id, "");
        var category = categoryService.getById(id);
        return ResponseEntity.ok(CategoryMapper.toResponseDTO(category));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        log.debug(String.format(REQUEST_LOG_TEMPLATE),
                "POST", "", categoryRequestDTO);
        var category = categoryService.create(categoryRequestDTO);
        return ResponseEntity.status(201).body(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable UUID id,
                                                      @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        log.debug(String.format(REQUEST_LOG_TEMPLATE),
                "PUT", id, categoryRequestDTO);
        var categoryDTO = categoryService.update(id, categoryRequestDTO);
        return ResponseEntity.ok(categoryDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateByFields(@PathVariable UUID id,
                                                              @RequestBody CategoryRequestDTO categoryRequestDTO) {
        log.debug(String.format(REQUEST_LOG_TEMPLATE),
                "PATCH", id, categoryRequestDTO);
        var categoryDTO = categoryService.updateByFields(id, categoryRequestDTO);
        return ResponseEntity.ok(categoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.debug(String.format(REQUEST_LOG_TEMPLATE),
                "DELETE", id, "");
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

}