CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public.account (
    id UUID PRIMARY KEY,
    roles JSONB NOT NULL,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE public.category (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    colour VARCHAR(9) NOT NULL,
    account_id UUID,
    FOREIGN KEY (account_id) REFERENCES public.account(id)
);

CREATE TABLE public.expense (
    id UUID PRIMARY KEY,
    amount MONEY NOT NULL,
    currency VARCHAR(3) NOT NULL,
    reimbursed BOOLEAN NOT NULL
);

CREATE TABLE public.shopping_list (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    owner_cost_factor INTEGER NOT NULL,
    category_id UUID NOT NULL,
    account_id UUID NOT NULL,
    FOREIGN KEY (category_id) REFERENCES public.category(id),
    FOREIGN KEY (account_id) REFERENCES public.account(id)
);

CREATE TABLE public.item (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    value DECIMAL NULL,
    active BOOLEAN NOT NULL,
    expense_id UUID NULL,
    shopping_list_id UUID,
    FOREIGN KEY (expense_id) REFERENCES public.expense(id),
    FOREIGN KEY (shopping_list_id) REFERENCES public.shopping_list(id)
);

CREATE TABLE public.account_shopping_list (
    cost_factor INTEGER NOT NULL,
    shopping_list_id UUID NOT NULL,
    account_id UUID NOT NULL,
    PRIMARY KEY (shopping_list_id, account_id),
    FOREIGN KEY (shopping_list_id) REFERENCES public.shopping_list (id),
    FOREIGN KEY (account_id) REFERENCES public.account(id)
);