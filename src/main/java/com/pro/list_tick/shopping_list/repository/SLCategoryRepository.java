package com.pro.list_tick.shopping_list.repository;

import com.pro.list_tick.shopping_list.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SLCategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findAllByAccountId(UUID userId);

    @Query("SELECT c.account.id FROM Category c WHERE c.id = :categoryId")
    UUID findUserIdById(@Param("categoryId") UUID categoryId);

//    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Category c WHERE c.name = :name AND c.user.id = :id")
    boolean existsByNameAndAccountId(String name, UUID accountId);

}
