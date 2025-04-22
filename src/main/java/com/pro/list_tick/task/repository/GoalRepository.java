package com.pro.list_tick.task.repository;

import com.pro.list_tick.task.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GoalRepository extends JpaRepository<Goal, UUID> {

    List<Goal> findAllByAccountId(UUID currentAccountId);
}
