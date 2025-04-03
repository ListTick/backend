package com.pro.list_tick.shopping_list.controller;

import com.pro.list_tick.shopping_list.dto.CategoryDTO;
import com.pro.list_tick.shopping_list.dto.CategoryInputDTO;
import com.pro.list_tick.shopping_list.service.CategoryService;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll() {
        var categories = categoryService.getAll();
        return ResponseEntity.ok(categories);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN') || (hasRole('ROLE_USER') && @securityService.isCurrentUser(#userId))")
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<CategoryDTO>> getAllByAccountId(@PathVariable UUID accountId) {
        var categories = categoryService.getAllByAccountId(accountId);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable UUID id) {
        var category = categoryService.getById(id);
        return ResponseEntity.ok(category);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN') || (hasRole('ROLE_USER') && @securityService.isCurrentUser(#userId))")
    @PostMapping("/{accountId}")
    public ResponseEntity<CategoryDTO> create(@PathVariable UUID accountId, @Valid @RequestBody CategoryInputDTO categoryInputDTO) {
        var category = categoryService.create(accountId, categoryInputDTO);
        return ResponseEntity.status(201).body(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable UUID id,
                                                      @Valid @RequestBody CategoryDTO categoryDTO) {
        var category = categoryService.update(id, categoryDTO);
        return ResponseEntity.ok(category);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateByFields(@PathVariable UUID id,
                                                              @RequestBody CategoryInputDTO categoryInputDTO) {
        var category = categoryService.updateByFields(id, categoryInputDTO);
        return ResponseEntity.ok(category);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN') || (hasRole('ROLE_USER') && @securityService.isCategoryAuthorized(#id))")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

}