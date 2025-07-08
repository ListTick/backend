package com.pro.list_tick.shopping_list.service.implementation;


import com.pro.list_tick.shared.api.AccountAPI;
import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.shopping_list.dto.CategoryDTO;
import com.pro.list_tick.shopping_list.dto.CategoryInputDTO;
import com.pro.list_tick.shopping_list.exception.CategoryException;
import com.pro.list_tick.shopping_list.mapper.CategoryMapper;
import com.pro.list_tick.shopping_list.model.Category;
import com.pro.list_tick.shopping_list.repository.SLCategoryRepository;
import com.pro.list_tick.shopping_list.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final AccountAPI accountAPI;
    private final CurrentAccountService currentAccountService;

    private final SLCategoryRepository categoryRepository;

    public Category getById(UUID id) {
        var accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting a shopping list category by the accountId: {}", accountId);

        var category = categoryRepository.findById(id)
            .orElseThrow(() -> {
                String errMessage = String.format("Category not found: %s", id);
                log.error(errMessage);
                return new CategoryException(HttpStatus.BAD_REQUEST, errMessage);
            });
        validateOwnership(category, accountId);
        return category;
    }

    public List<CategoryDTO> getAllDTOByAccountId() {
        var accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting all shopping list categories by the accountId: {}", accountId);

        var categories = categoryRepository.findAllByAccountId(accountId);
        return categories.stream()
                .map(CategoryMapper::toDTO)
                .toList();
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public CategoryDTO create(CategoryInputDTO categoryInputDTO) {
        var accountId = currentAccountService.getCurrentAccountId();
        log.debug("Creating a shopping list category for the accountId: {}, category name: {}",
                accountId, categoryInputDTO.getName());

        validateName(accountId, categoryInputDTO.getName());
        validateColour(categoryInputDTO);
        var category = CategoryMapper.toModel(categoryInputDTO);
        category.setAccountId(accountId);
        if (Objects.isNull(category.getColour())) {
            category.setColour(accountAPI.getDefaultShoppingListCategoryColour());
        }

        var savedCategory = categoryRepository.save(category);
        log.info("The category has been created: {}", savedCategory.getId());
        return CategoryMapper.toDTO(savedCategory);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public CategoryDTO update(UUID id, CategoryInputDTO categoryInputDTO) {
        var accountId = currentAccountService.getCurrentAccountId();
        log.debug("Updating the category: {}, for the accountId: {}", id, accountId);

        var category = getById(id);
        validateName(accountId, categoryInputDTO.getName());
        validateColour(categoryInputDTO);

        category.setName(categoryInputDTO.getName());
        if (Objects.isNull(categoryInputDTO.getColour())) {
            category.setColour(accountAPI.getDefaultShoppingListCategoryColour());
        } else {
            category.setColour(categoryInputDTO.getColour());
        }

        var savedCategory = categoryRepository.save(category);
        log.info("The category has been updated: {}", savedCategory.getId());
        return CategoryMapper.toDTO(savedCategory);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public CategoryDTO updateByFields(UUID id, CategoryInputDTO categoryInputDTO) {
        var accountId = currentAccountService.getCurrentAccountId();
        log.debug("Updating by fields the category: {}, for the accountId: {}", id, accountId);

        var category = getById(id);
        if (Objects.nonNull(categoryInputDTO.getName())) {
            validateName(accountId, categoryInputDTO.getName());
            category.setName(categoryInputDTO.getName());
        }
        if (Objects.nonNull(categoryInputDTO.getColour())) {
            validateColour(categoryInputDTO);
            category.setColour(categoryInputDTO.getColour());
        }

        var savedCategory = categoryRepository.save(category);
        log.info("The category has been updated by fields: {}", savedCategory.getId());
        return CategoryMapper.toDTO(savedCategory);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public void delete(UUID id) {
        log.debug("Deleting the shopping list category: {}", id);

        var category = getById(id);
        categoryRepository.delete(category);
        log.info("Shopping list category has been deleted: {}", id);
    }

    private void validateOwnership(Category category, UUID accountId) {
        if (!category.getAccountId().equals(accountId)) {
            String errMessage = String.format("Category not found: %s, for the user: %s",
                category.getId(), accountId);
            log.error(errMessage);
            throw new CategoryException(HttpStatus.CONFLICT, errMessage);
        }
    }

    private void validateName(UUID accountId, String name) {
        if (categoryRepository.existsByNameAndAccountId(name, accountId)) {
            String errMessage = String.format(
                "Category name already exists for the accountId: %s, category name: %s",
                accountId, name);
            log.error(errMessage);
            throw new CategoryException(HttpStatus.CONFLICT, errMessage);
        }
    }

    private void validateColour(CategoryInputDTO categoryInputDTO) {
        if (Objects.nonNull(categoryInputDTO.getColour()) && categoryInputDTO.getColour().length() != 7) {
            throw new CategoryException("Category colour, if specified, must be exactly 7 characters long");
        }
    }

}