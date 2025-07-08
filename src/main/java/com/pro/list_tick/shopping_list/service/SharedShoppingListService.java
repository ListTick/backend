package com.pro.list_tick.shopping_list.service;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.shopping_list.dto.AccountSharedWithDto;
import com.pro.list_tick.shopping_list.model.SharedShoppingList;
import com.pro.list_tick.shopping_list.model.ShoppingList;

public interface SharedShoppingListService {

  List<SharedShoppingList> getAllByAccountId(UUID accountId);
  List<UUID> getAllAccountsByShoppingListId(UUID shoppingListId);
  List<SharedShoppingList> createSharedShoppingLists(ShoppingList shoppingList,
                                                     List<AccountSharedWithDto> sharedWithAccounts);

}
