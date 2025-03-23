CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS public.account (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(60) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.account_settings (
    account_id UUID PRIMARY KEY,
    default_pomodoro_duration INT DEFAULT 25,
    default_pomodoro_break_duration INT DEFAULT 5,
    default_pomodoro_long_break_interval INT DEFAULT 4,
    default_pomodoro_long_break_duration INT DEFAULT 15,
    default_notification_break_reminder_time INT DEFAULT 3,
    is_long_break_enabled BOOLEAN DEFAULT true,
    default_task_tag_colour VARCHAR(9) NOT NULL,
    default_note_tag_colour VARCHAR(9) NOT NULL,
    default_shoppingList_category_colour VARCHAR(9) NOT NULL,
    default_bucketList_category_colour VARCHAR(9) NOT NULL,
    default_goal_category_colour VARCHAR(9) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES public.account(id)
);

CREATE TABLE IF NOT EXISTS public.role (
    name VARCHAR(255) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS public.account_role (
    name VARCHAR(255),
    account_id UUID,
    PRIMARY KEY (name, account_id),
    FOREIGN KEY (name) REFERENCES public.role(name),
    FOREIGN KEY (account_id) REFERENCES public.account(id)
);