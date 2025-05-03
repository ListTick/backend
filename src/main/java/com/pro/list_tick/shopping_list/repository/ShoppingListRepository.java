package com.pro.list_tick.shopping_list.repository;

import com.pro.list_tick.shopping_list.model.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, UUID> {

    List<ShoppingList> findAllByAccountId(UUID id);

    @Query("SELECT sl FROM ShoppingList sl LEFT JOIN FETCH sl.items WHERE sl.id = :id")
    Optional<ShoppingList> findByIdWithItems(UUID id);

    boolean existsByNameAndAccountId(String name, UUID accountId);

    boolean existsByIdAndAccountId(UUID id, UUID accountId);

}
