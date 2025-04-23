CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public.account (
    id UUID PRIMARY KEY,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE public.goal (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(512),
    priority int,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    realization_date TIMESTAMP,
    account_id UUID NOT NULL
);

CREATE TABLE public.tag (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    color VARCHAR(7),
    account_id UUID NOT NULL
);

CREATE TABLE public.task (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1024),
    total_pomodoros int,
    completed_pomodoros int,
    pomodoro_duration int,
    break_duration int,
    due_date TIMESTAMP,
    is_completed BOOLEAN NOT NULL,
    is_deleted BOOLEAN NOT NULL,
    goal_id UUID,
    tag_id UUID,
    account_id UUID NOT NULL,
    FOREIGN KEY (goal_id) REFERENCES public.goal(id),
    FOREIGN KEY (tag_id) REFERENCES public.tag(id)
);