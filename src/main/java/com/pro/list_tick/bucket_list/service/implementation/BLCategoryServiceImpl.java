package com.pro.list_tick.bucket_list.service.implementation;


import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.pro.list_tick.bucket_list.repository.BLCategoryRepository;
import com.pro.list_tick.bucket_list.service.BLCategoryService;
import com.pro.list_tick.shared.AccountAPI;
import com.pro.list_tick.shared.CurrentAccountAPI;
import com.pro.list_tick.bucket_list.dto.CategoryRequestDTO;
import com.pro.list_tick.bucket_list.dto.CategoryResponseDTO;
import com.pro.list_tick.bucket_list.exception.CategoryException;
import com.pro.list_tick.bucket_list.mapper.CategoryMapper;
import com.pro.list_tick.bucket_list.model.Category;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class BLCategoryServiceImpl implements BLCategoryService {

    private final AccountAPI accountAPI;
    private final CurrentAccountAPI currentAccountAPI;

    private final BLCategoryRepository categoryRepository;

    private static final UUID SHARED_CATEGORY_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    public Category getById(UUID id) {
        var accountId = currentAccountAPI.getCurrentAccountId();
        log.debug("Getting a bucket list category by the accountId: {}", accountId);

        var category = categoryRepository.findById(id)
            .orElseThrow(() -> {
                String errMessage = "Category not found";
                log.error("{}: {}", errMessage, id);
                return new CategoryException(HttpStatus.BAD_REQUEST, errMessage);
            });
        validateOwnership(category, accountId);
        return category;
    }

    public List<CategoryResponseDTO> getAllDTOByAccountId() {
        var accountId = currentAccountAPI.getCurrentAccountId();
        log.debug("Getting all bucket list categories by the accountId: {}", accountId);

        var categories = categoryRepository.findAllByAccountId(accountId);
        categories.add(getSharedCategory());
        return categories.stream()
                .map(CategoryMapper::toResponseDTO)
                .toList();
    }

    @Transactional(transactionManager = "bucketListTransactionManager")
    public CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO) {
        var accountId = currentAccountAPI.getCurrentAccountId();
        log.debug("Creating a bucket list category for the accountId: {}, category name: {}",
                accountId, categoryRequestDTO.name());

        validateName(accountId, categoryRequestDTO.name());
        validateColour(categoryRequestDTO);
        var category = CategoryMapper.toModel(categoryRequestDTO);
        category.setAccountId(accountId);
        if (Objects.isNull(category.getColour())) {
            category.setColour(accountAPI.getDefaultBucketListCategoryColour());
        }

        var savedCategory = categoryRepository.save(category);
        log.info("The category has been created: {}", savedCategory.getId());
        return CategoryMapper.toResponseDTO(savedCategory);
    }

    @Transactional(transactionManager = "bucketListTransactionManager")
    public CategoryResponseDTO update(UUID id, CategoryRequestDTO categoryRequestDTO) {
        var accountId = currentAccountAPI.getCurrentAccountId();
        log.debug("Updating the category: {}, for the accountId: {}", id, accountId);

        var category = getById(id);
        validateName(accountId, categoryRequestDTO.name());
        validateColour(categoryRequestDTO);

        category.setName(categoryRequestDTO.name());
        if (Objects.isNull(categoryRequestDTO.colour())) {
            category.setColour(accountAPI.getDefaultBucketListCategoryColour());
        } else {
            category.setColour(categoryRequestDTO.colour());
        }

        var savedCategory = categoryRepository.save(category);
        log.info("The category has been updated: {}", savedCategory.getId());
        return CategoryMapper.toResponseDTO(savedCategory);
    }

    @Transactional(transactionManager = "bucketListTransactionManager")
    public CategoryResponseDTO updateByFields(UUID id, CategoryRequestDTO categoryRequestDTO) {
        var accountId = currentAccountAPI.getCurrentAccountId();
        log.debug("Updating by fields the category: {}, for the accountId: {}", id, accountId);

        var category = getById(id);
        if (Objects.nonNull(categoryRequestDTO.name())) {
            validateName(accountId, categoryRequestDTO.name());
            category.setName(categoryRequestDTO.name());
        }
        if (Objects.nonNull(categoryRequestDTO.colour())) {
            validateColour(categoryRequestDTO);
            category.setColour(categoryRequestDTO.colour());
        }

        var savedCategory = categoryRepository.save(category);
        log.info("The category has been updated by fields: {}", savedCategory.getId());
        return CategoryMapper.toResponseDTO(savedCategory);
    }

    @Transactional(transactionManager = "bucketListTransactionManager")
    public void delete(UUID id) {
        log.debug("Deleting the bucket list category: {}", id);

        var category = getById(id);

        categoryRepository.delete(category);
        log.info("Bucket list category has been deleted: {}", id);
    }

    public Category getSharedCategory() {
        return getById(SHARED_CATEGORY_ID);
    }

    private void validateOwnership(Category category, UUID accountId) {
        if (category.getId().equals(SHARED_CATEGORY_ID)) {
            return;
        }
        if (!category.getAccountId().equals(accountId)) {
            String errMessage = "Access denied";
            log.error("{} {}: {}", errMessage, ",categoryId: ",category.getId());
            throw new CategoryException(HttpStatus.CONFLICT, errMessage);
        }
    }

    private void validateName(UUID accountId, String name) {
        if (categoryRepository.existsByNameAndAccountId(name, accountId)) {
            String errMessage = "Category name already exists";
          log.error("{} :{}", errMessage, name);
            throw new CategoryException(HttpStatus.CONFLICT, errMessage);
        }
    }

    private void validateColour(CategoryRequestDTO categoryRequestDTO) {
        if (Objects.nonNull(categoryRequestDTO.colour()) && categoryRequestDTO.colour().length() != 7) {
            throw new CategoryException("Category colour, if specified, must be exactly 7 characters long");
        }
    }

}