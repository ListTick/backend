package com.pro.list_tick.shopping_list.repository;

import com.pro.list_tick.shopping_list.model.SLAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SLAccountRepository extends JpaRepository<SLAccount, UUID> {
}
