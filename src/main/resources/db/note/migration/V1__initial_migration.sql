CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS public.note
(
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP,
    description TEXT,
    account_id UUID NOT NULL
);
