package com.pro.list_tick.shopping_list.repository;

import com.pro.list_tick.shopping_list.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    @Query("SELECT e from Expense e join ShoppingList sl ON e.shoppingList = sl")
    List<Expense> findAllByAccountId(UUID accountId);

    @Query("SELECT e FROM Expense e JOIN FETCH e.items items JOIN e.shoppingList sl WHERE sl.accountId = :accountId")
    List<Expense> findAllByAccountIdWithItems(UUID accountId);

}
