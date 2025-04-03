package com.pro.list_tick.task.service;

import com.pro.list_tick.task.mapper.TaskTagMapper;
import com.pro.list_tick.task.model.TaskTag;
import com.pro.list_tick.task.repository.TaskTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskTagService {
    private final TaskTagRepository taskTagRepository;

    @Transactional
    public void linkTaskWithTags(UUID taskId, List<UUID> tagIds) {
        List<TaskTag> taskTags = getAllTaskTagsByTaskId(taskId);

        List<UUID> idsToDelete = taskTags.stream()
                .map(TaskTag::getTagId)
                .toList();

        taskTagRepository.deleteAllByTagIdIn(idsToDelete);

        if (!tagIds.isEmpty()) {
            List<TaskTag> newTaskTags = tagIds.stream()
                    .map(tagId -> TaskTagMapper.toEntity(taskId, tagId))
                    .toList();

            taskTagRepository.saveAll(newTaskTags);
        }
    }

    @Transactional
    public void unlinkTaskFromTag(UUID taskTagId) {
        TaskTag taskTag = findTaskTagById(taskTagId);

        taskTagRepository.delete(taskTag);
    }

    public List<TaskTag> getAllTaskTagsByTaskId(UUID taskId) {
        return taskTagRepository.findAllByTaskId(taskId);
    }

    private TaskTag findTaskTagById(UUID taskTagId) {
        return taskTagRepository.findById(taskTagId)
                .orElseThrow(() -> new RuntimeException("TaskTag doesn't exist!"));
    }
}
