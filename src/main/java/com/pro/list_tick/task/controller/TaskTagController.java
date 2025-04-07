package com.pro.list_tick.task.controller;

import com.pro.list_tick.task.service.TaskTagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task-tag")
public class TaskTagController {
    private final TaskTagServiceImpl taskTagServiceImpl;

    @PutMapping
    public ResponseEntity<String> linkTaskWithTags(@RequestParam UUID taskId, @RequestBody List<UUID> tagIds) {
        taskTagServiceImpl.linkTaskWithTags(taskId, tagIds);

        return ResponseEntity.ok("Tags linked");
    }
}
