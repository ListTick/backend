package com.pro.list_tick.shopping_list.repository;

import com.pro.list_tick.shopping_list.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    @Query("SELECT e.account_id FROM Expense e JOIN ShoppingList ")
    List<Expense> findAllByAccountId(UUID accountId);



}
