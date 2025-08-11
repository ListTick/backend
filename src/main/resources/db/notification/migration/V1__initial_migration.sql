CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS public.notification (
     id UUID PRIMARY KEY,
     object_class VARCHAR(255),
     object_id UUID,
     description VARCHAR(255) NOT NULL,
     acknowledged BOOLEAN NOT NULL,
     account_id UUID NOT NULL
)