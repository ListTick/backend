package com.pro.list_tick.task.repository;

import com.pro.list_tick.task.model.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskTagRepository extends JpaRepository<TaskTag, UUID> {
    List<TaskTag> findAllByTaskId(UUID taskId);

    void deleteAllByTagIdIn(List<UUID> idsToDelete);

    void deleteAllByTaskId(UUID taskId);
}
