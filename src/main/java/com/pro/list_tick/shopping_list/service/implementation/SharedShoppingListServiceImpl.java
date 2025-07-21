package com.pro.list_tick.shopping_list.service.implementation;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.shared.api.AccountAPI;
import com.pro.list_tick.shopping_list.dto.AccountSharedWithRequestDto;
import com.pro.list_tick.shopping_list.exception.ShoppingListException;
import com.pro.list_tick.shopping_list.model.SharedShoppingList;
import com.pro.list_tick.shopping_list.model.ShoppingList;
import com.pro.list_tick.shopping_list.repository.SharedShoppingListRepository;
import com.pro.list_tick.shopping_list.service.SharedShoppingListService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SharedShoppingListServiceImpl implements SharedShoppingListService {

  private final AccountAPI accountAPI;
  private final SharedShoppingListRepository sharedShoppingListRepository;

  public List<SharedShoppingList> findAllActiveByAccountId(UUID accountId) {
    return sharedShoppingListRepository.findAllActiveByIdAccountId(accountId);
  }

  public List<UUID> getAllAccountsByShoppingListId(UUID shoppingListId) {
    return sharedShoppingListRepository.findAllAccountsById(shoppingListId);
  }

  public List<SharedShoppingList> createSharedShoppingLists(ShoppingList shoppingList,
                                                            List<AccountSharedWithRequestDto> sharedWithAccounts) {
    if (sharedWithAccounts == null || sharedWithAccounts.isEmpty()) {
      var errorMessage = "'sharedWithAccounts' cannot be null or empty while 'shared' is set to true";
      log.error(errorMessage);
      throw new ShoppingListException(HttpStatus.BAD_REQUEST, errorMessage);
    }

    log.debug("Creating shared lists for: {}", sharedWithAccounts);

    return sharedWithAccounts.stream()
        .map(accountSharedWithRequestDto -> {
          final var email = accountSharedWithRequestDto.email();
          final var accountId = accountAPI.getAccountIdByEmail(email);
          if (shoppingList.getAccountId().equals(accountId)) {
            var errorMessage = "Shopping list cannot be shared with the owner\'s own account";
            log.error("{} - accountId: {}", errorMessage, accountId);
            throw new ShoppingListException(HttpStatus.CONFLICT, errorMessage);
          }
          SharedShoppingList shared = new SharedShoppingList();
          shared.setShoppingListAndAccount(shoppingList, accountId);
          shared.setCostFactor(accountSharedWithRequestDto.costFactor());
          return sharedShoppingListRepository.save(shared);
        }).toList();
  }

  public String getEmail(UUID accountId) {
    return accountAPI.getEmailByAccountId(accountId);
  }

}
