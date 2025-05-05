package com.pro.list_tick.shopping_list.service;


import com.pro.list_tick.shared.api.AccountAPI;
import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.shopping_list.dto.CategoryDTO;
import com.pro.list_tick.shopping_list.dto.CategoryInputDTO;
import com.pro.list_tick.shopping_list.exception.CategoryException;
import com.pro.list_tick.shopping_list.mapper.CategoryMapper;
import com.pro.list_tick.shopping_list.repository.SLCategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    public static final String CATEGORY_NOT_FOUND = "Category not found: %s";
    public static final String CATEGORY_CONFLICT = "Category not found: %s, for the user: %s";

    private final AccountAPI accountAPI;

    private final SLCategoryRepository categoryRepository;
    private final CurrentAccountService currentAccountService;

    public List<CategoryDTO> getAllByAccountId() {
        final var accountId = currentAccountService.getCurrentAccountId();
        final var categories = categoryRepository.findAllByAccountId(accountId);
        return categories.stream()
                .map(CategoryMapper::toDTO)
                .toList();
    }

    public CategoryDTO getById(UUID id) {
        final var category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException(HttpStatus.BAD_REQUEST, String.format(CATEGORY_NOT_FOUND, id)));
        final var accountId = currentAccountService.getCurrentAccountId();
        if (!category.getAccountId().equals(accountId)) {
            throw new CategoryException(HttpStatus.CONFLICT, String.format(CATEGORY_CONFLICT, id, accountId));
        }
        return CategoryMapper.toDTO(category);
    }

    public CategoryDTO create(CategoryInputDTO categoryInputDTO) {
        final var accountId = currentAccountService.getCurrentAccountId();
        if (categoryRepository.existsByNameAndAccountId(categoryInputDTO.getName(), accountId)) {
            throw new CategoryException(HttpStatus.CONFLICT, String.format(
                    "Category name already exists: %s", categoryInputDTO.getName()));
        }
        var category = CategoryMapper.toModel(categoryInputDTO);
        category.setAccountId(accountId);
        if (Objects.isNull(category.getColour())) {
            category.setColour(accountAPI.getDefaultShoppingListCategoryColour());
        }
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    public CategoryDTO update(UUID id, CategoryInputDTO categoryInputDTO) {
        final var category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException(HttpStatus.BAD_REQUEST, String.format(CATEGORY_NOT_FOUND, id)));
        final var accountId = currentAccountService.getCurrentAccountId();
        if (!category.getAccountId().equals(accountId)) {
            throw new CategoryException(HttpStatus.CONFLICT, String.format(CATEGORY_CONFLICT, id, accountId));
        }
        category.setName(categoryInputDTO.getName());
        category.setColour(categoryInputDTO.getColour());
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    public CategoryDTO updateByFields(UUID id, CategoryInputDTO categoryInputDTO) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException(HttpStatus.BAD_REQUEST, String.format(CATEGORY_NOT_FOUND, id)));
        final var accountId = currentAccountService.getCurrentAccountId();
        if (!category.getAccountId().equals(accountId)) {
            throw new CategoryException(HttpStatus.CONFLICT, String.format(CATEGORY_CONFLICT, id, accountId));
        }
        if (Objects.nonNull(categoryInputDTO.getName())) {
            if (categoryRepository.existsByNameAndAccountId(categoryInputDTO.getName(), accountId)) {
                throw new CategoryException(HttpStatus.CONFLICT, String.format(
                        "Category name already exists: %s", categoryInputDTO.getName()));
            }
            category.setName(categoryInputDTO.getName());
        }
        if (Objects.nonNull(categoryInputDTO.getColour())) {
            category.setColour(categoryInputDTO.getColour());
        }
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    public void delete(UUID id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException(HttpStatus.BAD_REQUEST, String.format(CATEGORY_NOT_FOUND, id)));
        final var accountId = currentAccountService.getCurrentAccountId();
        if (!category.getAccountId().equals(accountId)) {
            throw new CategoryException(HttpStatus.CONFLICT, String.format(CATEGORY_CONFLICT, id, accountId));
        }
        categoryRepository.delete(category);
    }

}

//todo Add logs and exception handler