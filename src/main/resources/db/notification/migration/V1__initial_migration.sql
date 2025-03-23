CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public.account (
     id UUID PRIMARY KEY
);

CREATE TABLE public.notification (
     id UUID PRIMARY KEY,
     account_id UUID NOT NULL,
     object_class VARCHAR(255),
     object_id INT,
     description VARCHAR(255),
     FOREIGN KEY (account_id) REFERENCES public.account(id)
)