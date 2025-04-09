package com.pro.list_tick.task.service;

import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.task.dto.GoalRequestDto;
import com.pro.list_tick.task.dto.GoalResponseDto;
import com.pro.list_tick.task.mapper.GoalMapper;
import com.pro.list_tick.task.model.Account;
import com.pro.list_tick.task.model.Goal;
import com.pro.list_tick.task.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {
    private final GoalRepository goalRepository;
    private final CurrentAccountService currentAccountService;

    @Override
    public List<GoalResponseDto> getAllGoals() {
        UUID currentAccountId = currentAccountService.getCurrentAccountId();
        List<Goal> goals = goalRepository.findAllByAccountId(currentAccountId);

        return goals.stream().map(GoalMapper::toDto).toList();
    }

    @Override
    public GoalResponseDto getGoalById(UUID id) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found with id: " + id)); //TODO custom exception

        return GoalMapper.toDto(goal);
    }

    @Override
    public void createGoal(GoalRequestDto goalRequestDto) {
        UUID currentAccountId = currentAccountService.getCurrentAccountId();

        Goal goal = new Goal();
        goal.setDescription(goalRequestDto.description());
        goal.setPriority(goalRequestDto.priority());
        goal.setStartDate(goalRequestDto.startDate());
        goal.setEndDate(goalRequestDto.endDate());
        goal.setRealizationDate(goalRequestDto.realizationDate());

        Account account = new Account();
        account.setId(currentAccountId);

        goal.setAccount(account);

        goalRepository.save(goal);
    }

    @Override
    public void updateGoal(UUID id, GoalRequestDto goalRequestDto) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found with id: " + id)); //TODO custom exception

        goal.setDescription(goalRequestDto.description());
        goal.setPriority(goalRequestDto.priority());
        goal.setStartDate(goalRequestDto.startDate());
        goal.setEndDate(goalRequestDto.endDate());
        goal.setRealizationDate(goalRequestDto.realizationDate());

        goalRepository.save(goal);
    }

    @Override
    public void deleteGoal(UUID id) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found with id: " + id)); //TODO custom exception

        goalRepository.delete(goal);
    }
}
