package com.pro.list_tick.task.service;

import com.pro.list_tick.task.dto.TaskPageDto;
import com.pro.list_tick.task.dto.TaskRequestDto;
import com.pro.list_tick.task.dto.TaskResponseDto;
import com.pro.list_tick.task.mapper.TaskMapper;
import com.pro.list_tick.task.model.Task;
import com.pro.list_tick.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TagService tagService;
    private final TaskTagService taskTagService;
    private final TaskRepository taskRepository;

    @Transactional
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        UUID currentUserAccountId = UUID.fromString("0a247225-f9b9-4021-8848-75f56fb6fedc");  //TODO how do we check the currentUser
        Task task = TaskMapper.toEntity(taskRequestDto, currentUserAccountId);

        taskRepository.save(task);
        if (taskRequestDto.tagIds() == null) {
            return TaskMapper.toDto(task);
        }
        taskTagService.linkTaskWithTags(task.getId(), taskRequestDto.tagIds());

        return TaskMapper.toDto(task);
    }

    @Transactional
    public List<TaskResponseDto> getTasksByAccountId(List<String> tags) {
        List<Task> tasks;

        UUID currentUserAccountId = UUID.fromString("0a247225-f9b9-4021-8848-75f56fb6fedc");  //TODO how do we check the currentUser
        if (tags == null || tags.isEmpty()) {
            tasks = taskRepository.findAllNotDeletedByAccountId(currentUserAccountId);
        } else {
            tasks = taskRepository.findAllNotDeletedByAccountIdAndTags(currentUserAccountId, tags);
        }

        return tasks.stream()
                .map(task -> TaskMapper
                        .toDto(task, tagService.getAllTagsByTaskId(task.getId())))
                .toList();
    }


    @Transactional
    public TaskPageDto getArchivedTasksByAccountId(Pageable pageable) {
        UUID currentUserAccountId = UUID.fromString("0a247225-f9b9-4021-8848-75f56fb6fedc");  //TODO how do we check the currentUser
        Page<Task> tasksPage = taskRepository.findAllArchivedByAccountId(currentUserAccountId, pageable);

        List<TaskResponseDto> tasks =  tasksPage.getContent().stream()
                .map(task -> TaskMapper
                        .toDto(task, tagService.getAllTagsByTaskId(task.getId())))
                .toList();

        return new TaskPageDto(
                tasksPage.getTotalPages(),
                tasksPage.getTotalElements(),
                tasks
        );
    }

    @Transactional
    public TaskResponseDto updateTask(TaskRequestDto taskRequestDto, UUID taskId) {
        Task task = findTaskById(taskId);

        task.setName(taskRequestDto.name());
        task.setTotalPomodoros(taskRequestDto.totalPomodoros());
        task.setCompletedPomodoros(taskRequestDto.completedPomodoros());
        task.setPomodoroDuration(taskRequestDto.pomodoroDuration());
        task.setBreakDuration(taskRequestDto.breakDuration());
        task.setDueDate(taskRequestDto.dueDate());
        task.setCompleted(taskRequestDto.isCompleted());
        task.setDeleted(taskRequestDto.isDeleted());

        taskRepository.save(task);
        return TaskMapper.toDto(task);
    }


    @Transactional
    public void softDeleteTask(UUID taskId) {
        Task task = findTaskById(taskId);

        task.setDeleted(true);

        taskRepository.save(task);
    }

    @Transactional
    public TaskResponseDto updateCompletedPomodoros(Integer completedPomodoros, UUID taskId) {
        if (completedPomodoros == null) {
            throw new IllegalArgumentException("Completed pomodoros cannot be null");
        }

        Task task = findTaskById(taskId);
        Integer alreadyCompletedPomodoros = task.getCompletedPomodoros();
        task.setCompletedPomodoros(alreadyCompletedPomodoros + completedPomodoros);

        if (Objects.equals(task.getCompletedPomodoros(), task.getTotalPomodoros())) {
            task.setCompleted(true);
        }
        taskRepository.save(task);
        return TaskMapper.toDto(task);
    }

    public void toggleTaskComplete(UUID taskId) {
        Task task = findTaskById(taskId);

        task.setCompleted(!task.isCompleted());

        taskRepository.save(task);
    }

    public void deleteAllCompletedTasks() {
        UUID currentUserAccountId = UUID.fromString("0a247225-f9b9-4021-8848-75f56fb6fedc");  //TODO how do we check the currentUser

        taskRepository.deleteAllCompletedTasksByAccountId(currentUserAccountId);
    }

    public Task findTaskById(UUID taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task doesn't exist!"));
    }
}
