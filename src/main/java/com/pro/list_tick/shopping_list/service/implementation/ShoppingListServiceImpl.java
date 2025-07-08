package com.pro.list_tick.shopping_list.service.implementation;

import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.shopping_list.dto.AccountSharedWithDto;
import com.pro.list_tick.shopping_list.dto.ItemDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListInputDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListUpdateDTO;
import com.pro.list_tick.shopping_list.exception.ShoppingListException;
import com.pro.list_tick.shopping_list.mapper.ShoppingListMapper;
import com.pro.list_tick.shopping_list.model.SharedShoppingList;
import com.pro.list_tick.shopping_list.model.ShoppingList;
import com.pro.list_tick.shopping_list.repository.ShoppingListRepository;
import com.pro.list_tick.shopping_list.service.CategoryService;
import com.pro.list_tick.shopping_list.service.ItemService;
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
    private final ItemService itemService;
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

    public List<ShoppingListDTO> getAllDTOByAccountId() {
        final var accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting all shopping lists for the accountId: {}", accountId);

        var shoppingLists = shoppingListRepository.findAllByAccountId(accountId);
        var sharedShoppingLists = sharedShoppingListService.getAllByAccountId(accountId);
        List<ShoppingListDTO> dtoList = new ArrayList<>(shoppingLists.stream()
            .map(ShoppingListMapper::toDTO)
            .toList());
        dtoList.addAll(sharedShoppingLists.stream()
            .map(SharedShoppingList::getShoppingList)
            .map(ShoppingListMapper::toDTO)
            .toList());
        return dtoList;
    }

    public List<ItemDTO> getItemsByShoppingListId(UUID id) {
        return itemService.getAllByShoppingListId(id);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ShoppingListDTO create(ShoppingListInputDTO shoppingListInputDTO) {
        final var accountId = currentAccountService.getCurrentAccountId();
        log.info("Creating a shopping list for the account id: {}, name: {}",
            accountId, shoppingListInputDTO.getName());

        var shoppingList = ShoppingListMapper.toModel(shoppingListInputDTO);
        var category = categoryService.getById(shoppingListInputDTO.getCategoryId());

        if (shoppingListRepository.existsByNameAndAccountId(shoppingListInputDTO.getName(), accountId)) {
            throw new ShoppingListException(HttpStatus.CONFLICT,
                String.format("Shopping list name already exists: %s", shoppingListInputDTO.getName()));
        }

        shoppingList.setAccountId(accountId);
        shoppingList.setCategory(category);
        shoppingList.setItems(new ArrayList<>());
        shoppingList.setSharedShoppingLists(new ArrayList<>());
        shoppingList.setOwnerCostFactor(calculateCostFactor(shoppingListInputDTO.getShared(), shoppingListInputDTO.getSharedWithAccounts()));

        var savedShoppingList = shoppingListRepository.save(shoppingList);

        if (shoppingListInputDTO.getShared()) {
            List<SharedShoppingList> sharedLists = sharedShoppingListService
                .createSharedShoppingLists(savedShoppingList, shoppingListInputDTO.getSharedWithAccounts());
            savedShoppingList.getSharedShoppingLists().addAll(sharedLists);
        }

        log.info("Shopping list has been created: {}, accountId: {}, name: {}",
                savedShoppingList.getId(), savedShoppingList.getAccountId(), savedShoppingList.getName()
        );
        return ShoppingListMapper.toDTO(savedShoppingList);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ShoppingListDTO update(UUID id, ShoppingListUpdateDTO shoppingListUpdateDTO) {
        log.info("Updating the shopping list: {}", id);
        var shoppingList = getById(id);
        shoppingList.setName(shoppingListUpdateDTO.getName());
        shoppingList.setActive(shoppingListUpdateDTO.getActive());
        shoppingList.setCategory(categoryService.getById(shoppingListUpdateDTO.getCategoryId()));
        return ShoppingListMapper.toDTO(shoppingListRepository.save(shoppingList));
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ShoppingListDTO updateByFields(UUID id, ShoppingListUpdateDTO shoppingListUpdateDTO) {
        log.info("Updating the shopping list by fields: {}", id);
        var shoppingList = getById(id);
        if (Objects.nonNull(shoppingListUpdateDTO.getName())) {
            shoppingList.setName(shoppingListUpdateDTO.getName());
        }
        if (Objects.nonNull(shoppingListUpdateDTO.getActive())) {
            shoppingList.setActive(shoppingListUpdateDTO.getActive());
        }
        if (Objects.nonNull(shoppingListUpdateDTO.getCategoryId())) {
            var category = categoryService.getById(shoppingListUpdateDTO.getCategoryId());
            shoppingList.setCategory(category);
        }

        return ShoppingListMapper.toDTO(shoppingListRepository.save(shoppingList));
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

    private int calculateCostFactor(boolean isShared, List<AccountSharedWithDto> sharedWithAccounts) {
        log.debug("Calculating a cost factor for: {}", sharedWithAccounts);
        if (!isShared) {
            return 100;
        } else {
            int totalCostFactor = sharedWithAccounts.stream()
                    .mapToInt(AccountSharedWithDto::getCostFactor)
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
