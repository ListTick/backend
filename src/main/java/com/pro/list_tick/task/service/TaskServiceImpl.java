package com.pro.list_tick.task.service;

import com.pro.list_tick.shared.CurrentAccountAPI;
import com.pro.list_tick.task.dto.TaskPageDto;
import com.pro.list_tick.task.dto.TaskRequestDto;
import com.pro.list_tick.task.dto.TaskResponseDto;
import com.pro.list_tick.task.mapper.TaskMapper;
import com.pro.list_tick.task.model.Task;
import com.pro.list_tick.task.repository.TagRepository;
import com.pro.list_tick.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final CurrentAccountAPI currentAccountAPI;
    private final TagRepository tagRepository;

    @Transactional
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        UUID currentAccountId = currentAccountAPI.getCurrentAccountId();
        validateTaskRequest(taskRequestDto);
        Task task = TaskMapper.toEntity(taskRequestDto, currentAccountId);

        if (taskRequestDto.tagId() != null) {
            tagRepository.findById(taskRequestDto.tagId()).ifPresent(task::setTag);
        }

        taskRepository.save(task);

        return TaskMapper.toDto(task);
    }

    @Transactional
    public List<TaskResponseDto> getTasks(UUID tagId) {
        UUID currentAccountId = currentAccountAPI.getCurrentAccountId();
        List<Task> tasks;
        if (tagId == null) {
            tasks = taskRepository.findAllNotDeletedByAccountId(currentAccountId);
        } else {
            tasks = taskRepository.findAllNotDeletedByAccountIdAndTag(currentAccountId, tagId);
        }

        return tasks.stream().map(TaskMapper::toDto).toList();
    }


    @Transactional
    public TaskPageDto getArchivedTasks(Pageable pageable, UUID tagId) {
        UUID currentAccountId = currentAccountAPI.getCurrentAccountId();
        Page<Task> tasksPage;

        if (tagId == null) {
            tasksPage = taskRepository.findAllArchivedByAccountId(currentAccountId, pageable);
        } else {
            tasksPage = taskRepository.findAllArchivedByAccountIdAndTag(currentAccountId, pageable, tagId);
        }

        List<TaskResponseDto> tasks =  tasksPage
                .getContent()
                .stream()
                .map(TaskMapper::toDto).toList();

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

        if (taskRequestDto.tagId() != null) {
            tagRepository.findById(taskRequestDto.tagId()).ifPresent(task::setTag);
        }

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

        if (task.getCompletedPomodoros().equals(task.getTotalPomodoros())) {
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
        UUID currentAccountId = currentAccountAPI.getCurrentAccountId();
        taskRepository.deleteAllCompletedTasksByAccountId(currentAccountId);
    }

    private Task findTaskById(UUID taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task doesn't exist!"));
    }

    private void validateTaskRequest(TaskRequestDto taskRequestDto) {
        if (hasPomodoroDurationOrBreakDuration(taskRequestDto) && !hasCompletedPomodorosAndTotalPomodoros(taskRequestDto)) {
            throw new IllegalArgumentException("If pomodoro or break duration is set, " +
                    "both completed and total pomodoros must be provided.");
        }
    }

    private boolean hasPomodoroDurationOrBreakDuration(TaskRequestDto taskRequestDto) {
        return taskRequestDto.pomodoroDuration() != null || taskRequestDto.breakDuration() != null;
    }

    private boolean hasCompletedPomodorosAndTotalPomodoros(TaskRequestDto taskRequestDto) {
        return taskRequestDto.completedPomodoros() == null && taskRequestDto.totalPomodoros() == null;
    }
}
