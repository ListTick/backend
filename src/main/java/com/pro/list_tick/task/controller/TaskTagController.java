package com.pro.list_tick.task.controller;

import com.pro.list_tick.task.service.TaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task-tag")
public class TaskTagController {
    private final TaskTagService taskTagService;

    @PutMapping
    public ResponseEntity<String> linkTaskWithTags(@RequestParam UUID taskId, @RequestBody List<UUID> tagIds) {
        taskTagService.linkTaskWithTags(taskId, tagIds);

        return ResponseEntity.ok("Tags linked");
    }
}
