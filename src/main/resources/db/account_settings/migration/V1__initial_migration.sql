CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS public.account_settings (
    account_id UUID PRIMARY KEY,
    default_pomodoro_duration INT DEFAULT 25,
    default_pomodoro_break_duration INT DEFAULT 5,
    default_pomodoro_long_break_interval INT DEFAULT 4,
    default_pomodoro_long_break_duration INT DEFAULT 15,
    default_notification_break_reminder_time INT DEFAULT 3,
    long_break_enabled BOOLEAN DEFAULT true,
    default_task_tag_colour VARCHAR(7) DEFAULT '#494d50',
    default_note_tag_colour VARCHAR(7) DEFAULT '#494d50',
    default_shopping_list_category_colour VARCHAR(7) DEFAULT '#494d50',
    default_bucket_list_category_colour VARCHAR(7) DEFAULT '#494d50',
    default_goal_category_colour VARCHAR(7) DEFAULT '#494d50'
);