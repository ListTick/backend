package com.pro.list_tick.task.service;

import com.pro.list_tick.task.model.TaskTag;

import java.util.List;
import java.util.UUID;

public interface TaskTagService {
    void linkTaskWithTags(UUID taskId, List<UUID> tagIds);
    void unlinkTaskFromTag(UUID taskTagId);
    List<TaskTag> getAllTaskTagsByTaskId(UUID taskId);
}
