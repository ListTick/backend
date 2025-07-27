package com.pro.list_tick.shopping_list.repository;

import com.pro.list_tick.shopping_list.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SLItemRepository extends JpaRepository<Item, UUID> {

  @Query("SELECT i FROM Item i WHERE i.shoppingList.id = :shoppingListId")
  List<Item> findAllByShoppingListId(UUID shoppingListId);

  @Query("SELECT i FROM Item i WHERE i.shoppingList.id = :shoppingListId AND i.active IS TRUE")
  List<Item> findAllActiveByShoppingListId(UUID shoppingListId);

}
