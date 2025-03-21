CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS public.account (
    id uuid PRIMARY KEY,
    username varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    password varchar(60) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.account_settings (
    account_id uuid PRIMARY KEY,
    default_pomodoro_duration int DEFAULT 25,
    default_pomodoro_break_duration int DEFAULT 5,
    default_pomodoro_long_break_interval int DEFAULT 4,
    default_pomodoro_long_break_duration int DEFAULT 15,
    default_notification_break_reminder_time int DEFAULT 3,
    is_long_break_enabled boolean DEFAULT true,
    default_task_tag_colour varchar(9) NOT NULL,
    default_note_tag_colour varchar(9) NOT NULL,
    default_shoppingList_category_colour varchar(9) NOT NULL,
    default_bucketList_category_colour varchar(9) NOT NULL,
    default_goal_category_colour varchar(9) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES public.account(id)
);

CREATE TABLE IF NOT EXISTS public.role (
    name varchar(255) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS public.account_role (
    name varchar(255),
    account_id uuid,
    PRIMARY KEY (name, account_id),
    FOREIGN KEY (name) REFERENCES public.role(name),
    FOREIGN KEY (account_id) REFERENCES public.account(id)
);