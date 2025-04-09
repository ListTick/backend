package com.pro.list_tick.task.service;

import com.pro.list_tick.task.dto.GoalRequestDto;
import com.pro.list_tick.task.dto.GoalResponseDto;

import java.util.List;
import java.util.UUID;

public interface GoalService {

     List<GoalResponseDto> getAllGoals();
     GoalResponseDto getGoalById(UUID id);
     void createGoal(GoalRequestDto goalRequestDto);
     void updateGoal(UUID id, GoalRequestDto goalRequestDto);
     void deleteGoal(UUID id);
}
