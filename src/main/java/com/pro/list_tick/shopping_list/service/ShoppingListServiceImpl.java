package com.pro.list_tick.shopping_list.service;

import com.pro.list_tick.shared.api.AccountAPI;
import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.shopping_list.dto.AccountSharedWithDto;
import com.pro.list_tick.shopping_list.dto.ItemDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListInputDTO;
import com.pro.list_tick.shopping_list.exception.CategoryException;
import com.pro.list_tick.shopping_list.exception.ShoppingListException;
import com.pro.list_tick.shopping_list.mapper.ItemMapper;
import com.pro.list_tick.shopping_list.mapper.ShoppingListMapper;
import com.pro.list_tick.shopping_list.model.Category;
import com.pro.list_tick.shopping_list.model.SharedShoppingList;
import com.pro.list_tick.shopping_list.model.ShoppingList;
import com.pro.list_tick.shopping_list.repository.SLCategoryRepository;
import com.pro.list_tick.shopping_list.repository.SharedShoppingListRepository;
import com.pro.list_tick.shopping_list.repository.ShoppingListRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ShoppingListServiceImpl implements ShoppingListService {

    public static final String LIST_NOT_FOUND = "Shopping list not found: %s";
    public static final String LIST_CONFLICT = "Shopping list not found: %s, for the user: %s";

    private final CurrentAccountService currentAccountService;
    private final AccountAPI accountAPI;

    private final ShoppingListRepository shoppingListRepository;
    private final SLCategoryRepository categoryRepository;
    private final SharedShoppingListRepository sharedShoppingListRepository;

    public List<ShoppingListDTO> getAllByAccountId() {
        final var accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting all shopping lists for the accountId: {}", accountId);

        var shoppingLists = shoppingListRepository.findAllByAccountId(accountId);
        var sharedShoppingLists = sharedShoppingListRepository.findAllByIdAccountId(accountId);
        List<ShoppingListDTO> dtoList = new ArrayList<>(shoppingLists.stream().map(ShoppingListMapper::toDTO).toList());
        dtoList.addAll(sharedShoppingLists.stream().map(SharedShoppingList::getShoppingList).map(ShoppingListMapper::toDTO).toList());
        return dtoList;
    }

    public ShoppingListDTO getById(UUID id) {
        log.debug("Getting the shopping list: {}", id);
        final var shoppingList = shoppingListRepository.findById(id)
                .orElseThrow(() -> new ShoppingListException(String.format(LIST_NOT_FOUND, id)));
        final var accountId = currentAccountService.getCurrentAccountId();
        if (!shoppingList.getAccountId().equals(accountId)) {
            var errorMessage = String.format(LIST_CONFLICT, shoppingList.getId(), accountId);
            log.error(errorMessage);
            throw new ShoppingListException(HttpStatus.CONFLICT, errorMessage);
        }
        return ShoppingListMapper.toDTO(shoppingList);
    }

    public List<ItemDTO> getItemsByShoppingListId(UUID id) {
        log.debug("Getting items by shopping list id: {}", id);
        final var accountId = currentAccountService.getCurrentAccountId();
        if (!shoppingListRepository.existsByIdAndAccountId(id, accountId)) {
            var errorMessage = String.format(LIST_CONFLICT, id, accountId);
            log.error(errorMessage);
            throw new ShoppingListException(HttpStatus.CONFLICT, errorMessage);
        }
        var shoppingList = shoppingListRepository.findByIdWithItems(id)
                .orElseThrow(() -> new ShoppingListException(HttpStatus.NOT_FOUND, String.format(LIST_NOT_FOUND, id)));
        return shoppingList.getItems().stream().map(ItemMapper::toDTO).toList();
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ShoppingListDTO create(ShoppingListInputDTO shoppingListInputDTO) {
        final var accountId = currentAccountService.getCurrentAccountId();
        log.info("Creating a shopping list for the account id: {}, name: {}",accountId, shoppingListInputDTO.getName());

        var shoppingList = ShoppingListMapper.toModel(shoppingListInputDTO);
        var category = getCategory(shoppingListInputDTO.getCategoryId());

        if (shoppingListRepository.existsByNameAndAccountId(shoppingListInputDTO.getName(), accountId)) {
            var errorMessage = String.format("Shopping list name already exists: %s", shoppingListInputDTO.getName());
            log.error(errorMessage);
            throw new ShoppingListException(HttpStatus.CONFLICT, errorMessage);
        }

        shoppingList.setAccountId(accountId);
        shoppingList.setCategory(category);
        shoppingList.setItems(new ArrayList<>());
        shoppingList.setSharedShoppingLists(new ArrayList<>());
        shoppingList.setOwnerCostFactor(calculateCostFactor(shoppingListInputDTO.getShared(), shoppingListInputDTO.getSharedWithAccounts()));

        var savedShoppingList = shoppingListRepository.save(shoppingList);

        if (shoppingListInputDTO.getShared()) {
            log.info("Setting shared with users for the shopping list, account id: {}, name: {}",
                    accountId, shoppingListInputDTO.getName());
            if (shoppingListInputDTO.getSharedWithAccounts() == null
                    || shoppingListInputDTO.getSharedWithAccounts().isEmpty()) {
                var errorMessage = "'sharedWithAccounts' cannot be null or empty while 'shared' is set to true";
                log.error(errorMessage);
                throw new ShoppingListException(HttpStatus.BAD_REQUEST, errorMessage);
            }
            List<SharedShoppingList> sharedLists = createSharedLists(
                    savedShoppingList,
                    shoppingListInputDTO.getSharedWithAccounts()
            );
            savedShoppingList.getSharedShoppingLists().addAll(sharedLists);
        }

        log.info("Shopping list has been created: {}, accountId: {}, name: {}",
                savedShoppingList.getId(), savedShoppingList.getAccountId(), savedShoppingList.getName()
        );
        return ShoppingListMapper.toDTO(savedShoppingList);
    }
    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ShoppingListDTO update(UUID id, ShoppingListDTO shoppingListDTO) {
        log.info("Updating the shopping list: {}", id);
        var optional = shoppingListRepository.findById(id);
        var shoppingList = optional
                .orElseThrow(() -> {
                    var errorMessage = String.format(LIST_NOT_FOUND, id);
                    log.error(errorMessage);
                    return new ShoppingListException(HttpStatus.NOT_FOUND, errorMessage);
                });
        shoppingList.setName(shoppingListDTO.getName());
        shoppingList.setActive(shoppingListDTO.getActive());
        return ShoppingListMapper.toDTO(shoppingListRepository.save(shoppingList));
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ShoppingListDTO updateByFields(UUID id, ShoppingListDTO shoppingListDTO) {
        log.info("Updating the shopping list by fields: {}", id);
        var optional = shoppingListRepository.findById(id);
        var shoppingList = optional
                .orElseThrow(() -> {
                    var errorMessage = String.format(LIST_NOT_FOUND, id);
                    log.error(errorMessage);
                    return new ShoppingListException(HttpStatus.NOT_FOUND, errorMessage);
                });
        if (shoppingListDTO.getName() != null) {
            shoppingList.setName(shoppingListDTO.getName());
        }
        if (shoppingListDTO.getActive() != null) {
            shoppingList.setActive(shoppingListDTO.getActive());
        }
        return ShoppingListMapper.toDTO(shoppingListRepository.save(shoppingList));
    }

    public void delete(UUID id) {
        log.info("Deleting the shopping list: {}", id);
        var shoppingList = shoppingListRepository.findById(id)
                .orElseThrow(() -> {
                    var errorMessage = String.format(LIST_NOT_FOUND, id);
                    log.error(errorMessage);
                    return new ShoppingListException(HttpStatus.NOT_FOUND, errorMessage);
                });
        shoppingListRepository.delete(shoppingList);
    }

    private Category getCategory(UUID categoryId) {
        log.debug("Getting the shopping list category: {}", categoryId);
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    var errorMessage = String.format("Category not found: %s", categoryId);
                    log.error(errorMessage);
                    return new CategoryException(HttpStatus.NOT_FOUND, errorMessage);
                });
    }

    private List<SharedShoppingList> createSharedLists(ShoppingList shoppingList, List<AccountSharedWithDto> sharedWith) {
        log.debug("Creating shared lists for: {}", sharedWith);
        return sharedWith.stream()
                .map(accountSharedWithDto -> {
                    final var email = accountSharedWithDto.getEmail();
                    final var uuid = getAccountId(email);
                    SharedShoppingList shared = new SharedShoppingList();
                    shared.setShoppingListAndAccount(shoppingList, uuid);
                    shared.setCostFactor(accountSharedWithDto.getCostFactor());
                    return sharedShoppingListRepository.save(shared);
                }).toList();
    }

    private UUID getAccountId(String email) {
        log.debug("Getting an account id for the email: {}", email);
        return accountAPI.getAccountIdByEmail(email);
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
