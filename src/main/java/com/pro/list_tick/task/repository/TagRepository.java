package com.pro.list_tick.task.repository;

import com.pro.list_tick.task.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    List<Tag> findAllByAccountId(UUID accountId);

    @Query("SELECT t FROM Tag t WHERE t.name = :name AND t.account.id = :accountId")
    Tag findByName(String name, UUID accountId);
}
