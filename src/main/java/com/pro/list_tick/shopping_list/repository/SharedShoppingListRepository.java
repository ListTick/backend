package com.pro.list_tick.shopping_list.repository;

import com.pro.list_tick.shopping_list.model.AccountShoppingListId;
import com.pro.list_tick.shopping_list.model.SharedShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SharedShoppingListRepository extends JpaRepository<SharedShoppingList, AccountShoppingListId> {

    List<SharedShoppingList> findAllByAccountId(UUID userId);

    @Query("SELECT ssl.account.id FROM SharedShoppingList ssl WHERE  ssl.shoppingList.id = :shoppingListId")
    List<UUID> findAllAccountsById(@Param("shoppingListId")UUID shoppingListId);
}
