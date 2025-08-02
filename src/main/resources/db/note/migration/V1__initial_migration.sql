CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS public.note
(
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP,
    description TEXT
);
