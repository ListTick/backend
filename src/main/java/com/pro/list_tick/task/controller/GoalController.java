package com.pro.list_tick.task.controller;

import com.pro.list_tick.task.dto.GoalRequestDto;
import com.pro.list_tick.task.dto.GoalResponseDto;
import com.pro.list_tick.task.service.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/goal")
@RequiredArgsConstructor
public class GoalController {
    private final GoalService goalService;

    @PostMapping
    public ResponseEntity<GoalResponseDto> createGoal(@RequestBody @Valid GoalRequestDto goalRequestDto) {
        GoalResponseDto goalResponseDto = goalService.createGoal(goalRequestDto);

        return ResponseEntity.ok(goalResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<GoalResponseDto>> getAllGoals() {
        List<GoalResponseDto> goalResponseDto = goalService.getAllGoals();

        return ResponseEntity.ok(goalResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalResponseDto> getGoalById(@PathVariable UUID id) {
        GoalResponseDto goalResponseDto = goalService.getGoalById(id);

        return ResponseEntity.ok(goalResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalResponseDto> updateGoal(@PathVariable UUID id, @RequestBody @Valid GoalRequestDto goalRequestDto) {
        GoalResponseDto goalResponseDto = goalService.updateGoal(id, goalRequestDto);

        return ResponseEntity.ok(goalResponseDto);
    }

    @DeleteMapping("/{id}")
    public void deleteGoal(@PathVariable UUID id) {
        goalService.deleteGoal(id);
    }
}
