package com.pro.list_tick.shopping_list.service;

import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.shopping_list.dto.AccountSharedWithDto;
import com.pro.list_tick.shopping_list.dto.ItemDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListInputDTO;
import com.pro.list_tick.shopping_list.exception.AccountException;
import com.pro.list_tick.shopping_list.exception.CategoryException;
import com.pro.list_tick.shopping_list.exception.ShoppingListException;
import com.pro.list_tick.shopping_list.mapper.ItemMapper;
import com.pro.list_tick.shopping_list.mapper.ShoppingListMapper;
import com.pro.list_tick.shopping_list.model.Category;
import com.pro.list_tick.shopping_list.model.Account;
import com.pro.list_tick.shopping_list.model.SharedShoppingList;
import com.pro.list_tick.shopping_list.model.ShoppingList;
import com.pro.list_tick.shopping_list.repository.SLAccountRepository;
import com.pro.list_tick.shopping_list.repository.SLCategoryRepository;
import com.pro.list_tick.shopping_list.repository.SharedShoppingListRepository;
import com.pro.list_tick.shopping_list.repository.ShoppingListRepository;
import jakarta.validation.constraints.Email;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShoppingListServiceImpl implements ShoppingListService {

    public static final String LIST_NOT_FOUND = "Shopping list not found: %s";
    public static final String LIST_CONFLICT = "Shopping list not found: %s, for the user: %s";

    private final CurrentAccountService currentAccountService;

    private final ShoppingListRepository shoppingListRepository;
    private final SLCategoryRepository categoryRepository;
    private final SLAccountRepository accountRepository;
    private final SharedShoppingListRepository sharedShoppingListRepository;

    public List<ShoppingListDTO> getAllByAccountId() {
        final var accountId = currentAccountService.getCurrentAccountId();
        var shoppingLists = shoppingListRepository.findAllByAccountId(accountId);
        var sharedShoppingLists = sharedShoppingListRepository.findAllByAccountId(accountId);
        List<ShoppingListDTO> dtoList = new ArrayList<>(shoppingLists.stream().map(ShoppingListMapper::toDTO).toList());
        dtoList.addAll(sharedShoppingLists.stream().map(SharedShoppingList::getShoppingList).map(ShoppingListMapper::toDTO).toList());
        return dtoList;
    }

    public ShoppingListDTO getById(UUID id) {
        final var shoppingList = shoppingListRepository.findById(id)
                .orElseThrow(() -> new ShoppingListException(String.format(LIST_NOT_FOUND, id)));
        final var accountId = currentAccountService.getCurrentAccountId();
        if (!shoppingList.getAccountId().equals(accountId)) {
            throw new ShoppingListException(String.format(LIST_CONFLICT, shoppingList.getId(), accountId));
        }
        return ShoppingListMapper.toDTO(shoppingList);
    }

    public List<ItemDTO> getItemsByShoppingListId(UUID id) {
        final var accountId = currentAccountService.getCurrentAccountId();
        if (!shoppingListRepository.existsByIdAndAccountId(id, accountId)) {
            throw new ShoppingListException(String.format(LIST_CONFLICT, id, accountId));
        }
        var shoppingList = shoppingListRepository.findByIdWithItems(id)
                .orElseThrow(() -> new ShoppingListException(String.format(LIST_NOT_FOUND, id)));
        return shoppingList.getItems().stream().map(ItemMapper::toDTO).toList();
    }

    @Transactional
    public ShoppingListDTO create(ShoppingListInputDTO shoppingListInputDTO) {
        final var accountId = currentAccountService.getCurrentAccountId();
        var shoppingList = ShoppingListMapper.toModel(shoppingListInputDTO);
        var category = getCategory(shoppingListInputDTO.getCategoryId());

        if (shoppingListRepository.existsByNameAndAccountId(shoppingListInputDTO.getName(), accountId)) {
            throw new ShoppingListException("Shopping list name already exists.");
        }

        shoppingList.setAccountId(accountId);
        shoppingList.setCategory(category);
        shoppingList.setItems(new ArrayList<>());
        shoppingList.setSharedShoppingLists(new ArrayList<>());
        shoppingList.setOwnerCostFactor(calculateCostFactor(shoppingListInputDTO.isShared(), shoppingListInputDTO.getSharedWithAccounts()));

        var savedShoppingList = shoppingListRepository.save(shoppingList);

        if (shoppingListInputDTO.isShared()) {
            List<SharedShoppingList> sharedLists = createSharedLists(savedShoppingList, shoppingListInputDTO.getSharedWithAccounts());
            savedShoppingList.getSharedShoppingLists().addAll(sharedLists);
        }

        return ShoppingListMapper.toDTO(savedShoppingList);
    }
    //todo start from rest account service call
    @Transactional
    public ShoppingListDTO update(UUID id, ShoppingListDTO shoppingListDTO) {
        var optional = shoppingListRepository.findById(id);
        var shoppingList = optional
                .orElseThrow(() -> new ShoppingListException(String.format(LIST_NOT_FOUND, id)));
        shoppingList.setName(shoppingListDTO.getName());
        shoppingList.setActive(shoppingListDTO.getActive());
        return ShoppingListMapper.toDTO(shoppingListRepository.save(shoppingList));
    }

    @Transactional
    public ShoppingListDTO updateByFields(UUID id, ShoppingListDTO shoppingListDTO) {
        var optional = shoppingListRepository.findById(id);
        var shoppingList = optional
                .orElseThrow(() -> new ShoppingListException(String.format(LIST_NOT_FOUND, id)));
        if (shoppingListDTO.getName() != null) {
            shoppingList.setName(shoppingListDTO.getName());
        }
        if (shoppingListDTO.getActive() != null) {
            shoppingList.setActive(shoppingListDTO.getActive());
        }
        return ShoppingListMapper.toDTO(shoppingListRepository.save(shoppingList));
    }

    public void delete(UUID id) {
        var shoppingList = shoppingListRepository.findById(id)
                .orElseThrow(() -> new ShoppingListException(String.format(LIST_NOT_FOUND, id)));
        shoppingListRepository.delete(shoppingList);
    }

    private Category getCategory(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException(String.format("Category not found: %s", categoryId)));
    }

    private List<SharedShoppingList> createSharedLists(ShoppingList shoppingList, List<AccountSharedWithDto> sharedWith) {
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
        //todo write rest request to get the uuid from account service
        return UUID.randomUUID();
    }

    private int calculateCostFactor(boolean isShared, List<AccountSharedWithDto> sharedWithAccounts) {
        if (!isShared) {
            return 100;
        } else {
            int totalCostFactor = sharedWithAccounts.stream()
                    .mapToInt(AccountSharedWithDto::getCostFactor)
                    .sum();
            if (totalCostFactor > 100) {
                throw new ShoppingListException("Total cost factor cannot exceed 100.");
            }
            return 100 - totalCostFactor;
        }
    }

}
