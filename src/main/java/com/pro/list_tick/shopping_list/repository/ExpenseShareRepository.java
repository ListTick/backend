package com.pro.list_tick.shopping_list.repository;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.shopping_list.model.ExpenseShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, UUID> {
  List<ExpenseShare> findAllByAccountId(UUID accountId);

  List<ExpenseShare> findAllByAccountIdAndReimbursed(UUID accountId, Boolean reimbursed);
}
