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


    public List<CategoryDTO> getAllByAccountId() {
        var accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting all shopping list categories by the accountId: {}", accountId);

        var categories = categoryRepository.findAllByAccountId(accountId);
        return categories.stream()
                .map(CategoryMapper::toDTO)
                .toList();
    }

    public CategoryDTO getById(UUID id) {
        var accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting a shopping list category by the accountId: {}", accountId);

        var category = getCategory(id);
        checkCategoryOwnership(category, accountId);
        return CategoryMapper.toDTO(category);
    }

    public CategoryDTO create(CategoryInputDTO categoryInputDTO) {
        var accountId = currentAccountService.getCurrentAccountId();
        log.info("Creating a shopping list category for the accountId: {}, category name: {}",
                accountId, categoryInputDTO.getName());

        if (categoryRepository.existsByNameAndAccountId(categoryInputDTO.getName(), accountId)) {
            log.error("Category name already exists for the accountId: {}, category name: {}",
                    accountId, categoryInputDTO.getName());
            throw new CategoryException(HttpStatus.CONFLICT, String.format(
                    "Category name already exists: %s", categoryInputDTO.getName()));
        }
        var category = CategoryMapper.toModel(categoryInputDTO);
        category.setAccountId(accountId);
        if (Objects.isNull(category.getColour())) {
            category.setColour(accountAPI.getDefaultShoppingListCategoryColour());
        }

        log.debug("Saving the shopping list category for the accountId: {}, category name: {}",
                accountId, categoryInputDTO.getName());
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    public CategoryDTO update(UUID id, CategoryInputDTO categoryInputDTO) {
        var accountId = currentAccountService.getCurrentAccountId();
        log.info("Updating the category: {}, for the accountId: {}", id, accountId);

        var category = getCategory(id);
        checkCategoryOwnership(category, accountId);
        category.setName(categoryInputDTO.getName());
        category.setColour(categoryInputDTO.getColour());

        log.debug("Saving the updated shopping list category: {}, accountId: {}",
                id, accountId);
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    public CategoryDTO updateByFields(UUID id, CategoryInputDTO categoryInputDTO) {
        var accountId = currentAccountService.getCurrentAccountId();
        log.info("Updating by fields the category: {}, for the accountId: {}", id, accountId);

        var category = getCategory(id);
        checkCategoryOwnership(category, accountId);
        if (Objects.nonNull(categoryInputDTO.getName())) {
            if (categoryRepository.existsByNameAndAccountId(categoryInputDTO.getName(), accountId)) {
                log.error("Category name already exists for the accountId: {}, category name: {}",
                        accountId, categoryInputDTO.getName());
                throw new CategoryException(HttpStatus.CONFLICT, String.format(
                        "Category name already exists: %s", categoryInputDTO.getName()));
            }
            category.setName(categoryInputDTO.getName());
        }
        if (Objects.nonNull(categoryInputDTO.getColour())) {
            category.setColour(categoryInputDTO.getColour());
        }

        log.debug("Saving the updated by fields shopping list category: {}, accountId: {}",
                id, accountId);
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    public void delete(UUID id) {
        var accountId = currentAccountService.getCurrentAccountId();
        log.info("Deleting the shopping list category: {}, for the accountId: {}", id, accountId);

        var category = getCategory(id);
        checkCategoryOwnership(category, accountId);
        categoryRepository.delete(category);
        log.info("Shopping list category has been deleted: {}, for the accountId: {}", id, accountId);
    }

    private Category getCategory(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Category not found: {}", id);
                    return new CategoryException(HttpStatus.BAD_REQUEST, String.format(
                            "Category not found: %s", id));
                });
    }

    private void checkCategoryOwnership(Category category, UUID accountId) {
        if (!category.getAccountId().equals(accountId)) {
            log.error("Category not found: {}, for the user: {}", category.getId(), accountId);
            throw new CategoryException(HttpStatus.CONFLICT, String.format(
                    "Category not found: %s, for the user: %s", category.getId(), accountId));
        }
    }

}

//todo Add exception handler