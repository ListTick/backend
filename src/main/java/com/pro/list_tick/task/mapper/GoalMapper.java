package com.pro.list_tick.task.mapper;


import com.pro.list_tick.task.dto.GoalRequestDto;
import com.pro.list_tick.task.dto.GoalResponseDto;
import com.pro.list_tick.task.model.Goal;

import java.util.UUID;

public class GoalMapper {

    private GoalMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static GoalResponseDto toDto(Goal goal) {
        return new GoalResponseDto(
                goal.getId(),
                goal.getName(),
                goal.getDescription(),
                goal.getPriority(),
                goal.getStartDate(),
                goal.getEndDate(),
                goal.getRealizationDate()
        );
    }

    public static Goal toEntity(GoalRequestDto goalRequestDto, UUID accountId) {
        Goal goal = new Goal();
        goal.setName(goalRequestDto.name());
        goal.setDescription(goalRequestDto.description());
        goal.setPriority(goalRequestDto.priority());
        goal.setStartDate(goalRequestDto.startDate());
        goal.setEndDate(goalRequestDto.endDate());
        goal.setRealizationDate(goalRequestDto.realizationDate());
        goal.setAccountId(accountId);

        return goal;
    }
}
