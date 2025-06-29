package com.pro.list_tick.task.service;

import com.pro.list_tick.shared.current_user.CurrentAccountService;
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
    private final CurrentAccountService currentAccountService;
    private final TagRepository tagRepository;

    @Transactional
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        UUID currentAccountId = currentAccountService.getCurrentAccountId();

        Task task = TaskMapper.toEntity(taskRequestDto, currentAccountId);

        if (taskRequestDto.tagId() != null) {
            tagRepository.findById(taskRequestDto.tagId()).ifPresent(task::setTag);
        }

        taskRepository.save(task);

        return TaskMapper.toDto(task);
    }

    @Transactional
    public List<TaskResponseDto> getTasks(String tag) {
        UUID currentAccountId = currentAccountService.getCurrentAccountId();
        List<Task> tasks = taskRepository.findAllNotDeletedByAccountId(currentAccountId);
        //todo: add tag

        return tasks.stream().map(TaskMapper::toDto).toList();
    }


    @Transactional
    public TaskPageDto getArchivedTasks(Pageable pageable) {
        UUID currentAccountId = currentAccountService.getCurrentAccountId();
        Page<Task> tasksPage = taskRepository.findAllArchivedByAccountId(currentAccountId, pageable);

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
        UUID currentAccountId = currentAccountService.getCurrentAccountId();
        taskRepository.deleteAllCompletedTasksByAccountId(currentAccountId);
    }

    private Task findTaskById(UUID taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task doesn't exist!"));
    }
}
