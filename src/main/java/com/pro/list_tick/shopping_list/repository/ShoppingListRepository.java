package com.pro.list_tick.shopping_list.repository;

import com.pro.list_tick.shopping_list.model.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, UUID> {

    List<ShoppingList> findAllByAccountId(UUID id);

    @Query("SELECT sl.account.id FROM ShoppingList sl WHERE sl.id = :shoppingListId")
    UUID findUserIdById(@Param("shoppingListId")UUID shoppingListId);

    boolean existsByNameAndAccountId(String name, UUID accountId);

}
