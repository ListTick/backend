package com.pro.list_tick.task.mapper;


import com.pro.list_tick.task.dto.GoalResponseDto;
import com.pro.list_tick.task.model.Goal;

public class GoalMapper {

    private GoalMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static GoalResponseDto toDto(Goal goal) {
        return new GoalResponseDto(
                goal.getId(),
                goal.getDescription(),
                goal.getPriority(),
                goal.getStartDate(),
                goal.getEndDate(),
                goal.getRealizationDate()
        );
    }
}
