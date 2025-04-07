package com.pro.list_tick.task.controller;

import com.pro.list_tick.task.dto.TaskPageDto;
import com.pro.list_tick.task.dto.TaskRequestDto;
import com.pro.list_tick.task.dto.TaskResponseDto;
import com.pro.list_tick.task.service.TaskServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskServiceImpl taskServiceImpl;

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody TaskRequestDto taskRequestDto) {
        TaskResponseDto taskResponseDto = taskServiceImpl.createTask(taskRequestDto);

        return ResponseEntity.ok(taskResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getTasks(
            @RequestParam(required = false) List<String> tags
    ) {
        List<TaskResponseDto> taskResponseDto = taskServiceImpl.getTasksByAccountId(tags);

        return ResponseEntity.ok(taskResponseDto);
    }

    @GetMapping("/archive")
    public ResponseEntity<TaskPageDto> getArchivedTasks(@PageableDefault(size = 20, sort = "name") Pageable pageable) {
        TaskPageDto taskResponseDto = taskServiceImpl.getArchivedTasksByAccountId(pageable);

        return ResponseEntity.ok(taskResponseDto);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTask(@Valid @RequestBody TaskRequestDto taskDto,
                                     @PathVariable UUID taskId) {
        TaskResponseDto taskResponseDto = taskServiceImpl.updateTask(taskDto, taskId);

        return ResponseEntity.ok(taskResponseDto);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> softDeleteTask(@PathVariable UUID taskId) {
        taskServiceImpl.softDeleteTask(taskId);

        return ResponseEntity.ok("Task deleted");
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateCompletedPomodoros(@PathVariable UUID taskId,
                                                                    @PositiveOrZero Integer completedPomodoros) {
        TaskResponseDto taskResponseDto = taskServiceImpl.updateCompletedPomodoros(completedPomodoros, taskId);

        return ResponseEntity.ok(taskResponseDto);
    }

    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<String> toggleTaskComplete(@PathVariable UUID taskId) {
        taskServiceImpl.toggleTaskComplete(taskId);

        return ResponseEntity.ok("Task completion status changed");
    }

    @PostMapping("/deleteCompleted")
    public ResponseEntity<String> deleteAllCompletedTasks() {
        taskServiceImpl.deleteAllCompletedTasks();

        return ResponseEntity.ok("All completed tasks were deleted");
    }
}
