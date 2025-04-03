package com.pro.list_tick.task.mapper;

import com.pro.list_tick.task.dto.TaskTagRequestDto;
import com.pro.list_tick.task.dto.TaskTagResponseDto;
import com.pro.list_tick.task.model.TaskTag;

import java.util.UUID;

public class TaskTagMapper {

    private TaskTagMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static TaskTag toEntity(TaskTagRequestDto taskTagRequestDto) {

        TaskTag taskTag = new TaskTag();
        taskTag.setTaskId(taskTagRequestDto.taskId());
        taskTag.setTagId(taskTagRequestDto.tagId());

        return taskTag;
    }

    public static TaskTag toEntity(UUID taskId, UUID tagId) {

        TaskTag taskTag = new TaskTag();
        taskTag.setTaskId(taskId);
        taskTag.setTagId(tagId);

        return taskTag;
    }

    public static TaskTagResponseDto toDto(TaskTag taskTag) {
        return new TaskTagResponseDto(
                taskTag.getTaskId(),
                taskTag.getTagId()
        );
    }
}
