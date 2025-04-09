package com.pro.list_tick.task.controller;

import com.pro.list_tick.task.dto.GoalRequestDto;
import com.pro.list_tick.task.dto.GoalResponseDto;
import com.pro.list_tick.task.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController {
    private final GoalService goalService;

    @PostMapping
    public void createGoal(GoalRequestDto goalRequestDto) {
        goalService.createGoal(goalRequestDto);
    }

    @GetMapping
    public List<GoalResponseDto> getAllGoals() {
        return goalService.getAllGoals();
    }

    @GetMapping("/{id}")
    public GoalResponseDto getGoalById(@PathVariable UUID id) {
        return goalService.getGoalById(id);
    }

    @PutMapping("/{id}")
    public void updateGoal(@PathVariable UUID id, GoalRequestDto goalRequestDto) {
        goalService.updateGoal(id, goalRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteGoal(@PathVariable UUID id) {
        goalService.deleteGoal(id);
    }
}
