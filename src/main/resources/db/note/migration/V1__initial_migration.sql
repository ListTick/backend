CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS public.account (
    id UUID PRIMARY KEY,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS public.category (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    colour VARCHAR(9) NOT NULL,
    account_id UUID,
    FOREIGN KEY (account_id) REFERENCES public.account(id)
);

CREATE TABLE IF NOT EXISTS public.note
(
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL,
    category_id UUID NOT NULL,
    description VARCHAR(255),
    FOREIGN KEY (account_id) REFERENCES public.account (id),
    FOREIGN KEY (category_id) REFERENCES public.category (id)
);
