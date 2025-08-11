package com.pro.list_tick.task.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PomodoroFieldsValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPomodoroFields {
    String message() default "If any pomodoro-related fields are present, all must be provided.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}