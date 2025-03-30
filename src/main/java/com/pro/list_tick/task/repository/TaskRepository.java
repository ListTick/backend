package com.pro.list_tick.task.repository;

import com.pro.list_tick.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
