package com.pro.list_tick.shopping_list.service.implementation;

import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.shopping_list.dto.AccountSharedWithRequestDto;
import com.pro.list_tick.shopping_list.dto.ShoppingListResponseDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListRequestDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListRequestUpdateDTO;
import com.pro.list_tick.shopping_list.exception.ShoppingListException;
import com.pro.list_tick.shopping_list.mapper.ShoppingListMapper;
import com.pro.list_tick.shopping_list.model.SharedShoppingList;
import com.pro.list_tick.shopping_list.model.ShoppingList;
import com.pro.list_tick.shopping_list.repository.ShoppingListRepository;
import com.pro.list_tick.shopping_list.service.CategoryService;
import com.pro.list_tick.shopping_list.service.SharedShoppingListService;
import com.pro.list_tick.shopping_list.service.ShoppingListService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ShoppingListServiceImpl implements ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;

    private final CurrentAccountService currentAccountService;
    private final CategoryService categoryService;
    private final SharedShoppingListService sharedShoppingListService;

    public ShoppingList getById(UUID id) {
        log.debug("Getting the shopping list: {}", id);
        final var shoppingList = shoppingListRepository.findById(id)
            .orElseThrow(() -> new ShoppingListException(String.format("Shopping list not found: %s", id)));

        final var accountId = currentAccountService.getCurrentAccountId();
        if (!validateAccess(accountId, shoppingList) &&
            !validateSharedAccess(accountId, shoppingList)) {
            throw new ShoppingListException(HttpStatus.FORBIDDEN,
                String.format("User doesn't have an access to the shopping list: %s", shoppingList.getId()));
        }
        return shoppingList;
    }

    public List<ShoppingListResponseDTO> getAllDTOByAccountId() {
        final var accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting all shopping lists for the accountId: {}", accountId);

        var shoppingLists = shoppingListRepository.findAllByAccountId(accountId);
        var sharedShoppingLists = sharedShoppingListService.getAllByAccountId(accountId);
        List<ShoppingListResponseDTO> dtoList = new ArrayList<>(shoppingLists.stream()
            .map(ShoppingListMapper::toResponseDTO)
            .toList());
        dtoList.addAll(sharedShoppingLists.stream()
            .map(SharedShoppingList::getShoppingList)
            .map(ShoppingListMapper::toResponseDTO)
            .toList());
        return dtoList;
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ShoppingListResponseDTO create(ShoppingListRequestDTO shoppingListRequestDTO) {
        final var accountId = currentAccountService.getCurrentAccountId();
        log.info("Creating a shopping list for the account id: {}, name: {}",
            accountId, shoppingListRequestDTO.name());

        var shoppingList = ShoppingListMapper.toModel(shoppingListRequestDTO);
        var category = categoryService.getById(shoppingListRequestDTO.categoryId());

        if (shoppingListRepository.existsByNameAndAccountId(shoppingListRequestDTO.name(), accountId)) {
            throw new ShoppingListException(HttpStatus.CONFLICT,
                String.format("Shopping list name already exists: %s", shoppingListRequestDTO.name()));
        }

        shoppingList.setAccountId(accountId);
        shoppingList.setCategory(category);
        shoppingList.setItems(new ArrayList<>());
        shoppingList.setSharedShoppingLists(new ArrayList<>());
        shoppingList.setOwnerCostFactor(calculateCostFactor(
            shoppingListRequestDTO.shared(),
            shoppingListRequestDTO.sharedWithAccounts())
        );

        var savedShoppingList = shoppingListRepository.save(shoppingList);

        if (shoppingListRequestDTO.shared()) {
            List<SharedShoppingList> sharedLists = sharedShoppingListService
                .createSharedShoppingLists(savedShoppingList, shoppingListRequestDTO.sharedWithAccounts());
            savedShoppingList.getSharedShoppingLists().addAll(sharedLists);
        }

        log.info("Shopping list has been created: {}, accountId: {}, name: {}",
                savedShoppingList.getId(), savedShoppingList.getAccountId(), savedShoppingList.getName()
        );
        return ShoppingListMapper.toResponseDTO(savedShoppingList);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ShoppingListResponseDTO update(UUID id, ShoppingListRequestUpdateDTO shoppingListRequestUpdateDTO) {
        log.info("Updating the shopping list: {}", id);
        var shoppingList = getById(id);
        shoppingList.setName(shoppingListRequestUpdateDTO.name());
        shoppingList.setActive(shoppingListRequestUpdateDTO.active());
        shoppingList.setCategory(categoryService.getById(shoppingListRequestUpdateDTO.categoryId()));
        return ShoppingListMapper.toResponseDTO(shoppingListRepository.save(shoppingList));
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ShoppingListResponseDTO updateByFields(UUID id, ShoppingListRequestUpdateDTO shoppingListRequestUpdateDTO) {
        log.info("Updating the shopping list by fields: {}", id);
        var shoppingList = getById(id);
        if (Objects.nonNull(shoppingListRequestUpdateDTO.name())) {
            shoppingList.setName(shoppingListRequestUpdateDTO.name());
        }
        if (Objects.nonNull(shoppingListRequestUpdateDTO.active())) {
            shoppingList.setActive(shoppingListRequestUpdateDTO.active());
        }
        if (Objects.nonNull(shoppingListRequestUpdateDTO.categoryId())) {
            var category = categoryService.getById(shoppingListRequestUpdateDTO.categoryId());
            shoppingList.setCategory(category);
        }

        return ShoppingListMapper.toResponseDTO(shoppingListRepository.save(shoppingList));
    }

    public void delete(UUID id) {
        log.info("Deleting the shopping list: {}", id);
        final var shoppingList = shoppingListRepository.findById(id)
            .orElseThrow(() -> new ShoppingListException(String.format("Shopping list not found: %s", id)));

        var accountId = currentAccountService.getCurrentAccountId();
        validateAccess(accountId, shoppingList);
        shoppingListRepository.delete(shoppingList);
    }

    public Boolean validateAccess(UUID accountId, ShoppingList shoppingList) {
        log.debug("Validating the shopping list access: {}", shoppingList.getId());

        return !accountId.equals(shoppingList.getAccountId());
    }

    public Boolean validateSharedAccess(UUID accountId, ShoppingList shoppingList) {
        log.debug("Validating the shopping list shared access");

        var accountIds = sharedShoppingListService.getAllAccountsByShoppingListId(shoppingList.getId());
        return accountIds.stream().anyMatch(id -> id.equals(accountId));
    }

    private int calculateCostFactor(boolean isShared, List<AccountSharedWithRequestDto> sharedWithAccounts) {
        log.debug("Calculating a cost factor for: {}", sharedWithAccounts);
        if (!isShared) {
            return 100;
        } else {
            int totalCostFactor = sharedWithAccounts.stream()
                    .mapToInt(AccountSharedWithRequestDto::costFactor)
                    .sum();
            if (totalCostFactor > 100) {
                var errorMessage = "Total cost factor cannot exceed 100.";
                log.error(errorMessage);
                throw new ShoppingListException(HttpStatus.BAD_REQUEST, errorMessage);
            }
            return 100 - totalCostFactor;
        }
    }

}
