package com.pro.list_tick.shopping_list.repository;

import com.pro.list_tick.shopping_list.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

  @Query("select i from Item i where i.shoppingList.id = :shoppingListId")
  List<Item> findAllByShoppingListId(UUID shoppingListId);

}
