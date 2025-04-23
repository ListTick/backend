package com.pro.list_tick.shopping_list.controller;

import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.shopping_list.dto.CategoryDTO;
import com.pro.list_tick.shopping_list.dto.CategoryInputDTO;
import com.pro.list_tick.shopping_list.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CurrentAccountService currentAccountService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllByAccountId() {
        var accountId = currentAccountService.getCurrentAccountId();
        var categories = categoryService.getAllByAccountId(accountId);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable UUID id) {
        //todo check if the token's user id matches the resource maybe custom query?
        var category = categoryService.getById(id);
        return ResponseEntity.ok(category);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN') || (hasRole('ROLE_USER') && @securityService.isCurrentUser(#userId))")
    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryInputDTO categoryInputDTO) {
        //todo create the proper logic
       // var category = categoryService.create(categoryInputDTO);
        var category = new CategoryDTO();
        return ResponseEntity.status(201).body(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable UUID id,
                                                      @Valid @RequestBody CategoryDTO categoryDTO) {
        //todo maybe dto without an id, verify if the user has an access to the resource
        var category = categoryService.update(id, categoryDTO);
        return ResponseEntity.ok(category);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateByFields(@PathVariable UUID id,
                                                              @RequestBody CategoryInputDTO categoryInputDTO) {
        //todo maybe dto without an id, verify if the user has an access to the resource
        var category = categoryService.updateByFields(id, categoryInputDTO);
        return ResponseEntity.ok(category);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN') || (hasRole('ROLE_USER') && @securityService.isCategoryAuthorized(#id))")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        //todo check the access
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

}