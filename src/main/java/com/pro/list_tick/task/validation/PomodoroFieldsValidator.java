package com.pro.list_tick.task.validation;

import com.pro.list_tick.task.dto.TaskRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PomodoroFieldsValidator implements ConstraintValidator<ValidPomodoroFields, TaskRequestDto> {

    @Override
    public boolean isValid(TaskRequestDto dto, ConstraintValidatorContext constraintValidatorContext) {
        boolean anyPresent = dto.pomodoroDuration() != null
                || dto.breakDuration() != null
                || dto.completedPomodoros() != null
                || dto.totalPomodoros() != null;

        boolean allPresent = dto.pomodoroDuration() != null
                && dto.breakDuration() != null
                && dto.completedPomodoros() != null
                && dto.totalPomodoros() != null;

        return !anyPresent || allPresent;
    }
}
