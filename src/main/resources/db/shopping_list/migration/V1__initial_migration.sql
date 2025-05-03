CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public.category (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    colour VARCHAR(7) NOT NULL,
    account_id UUID NOT NULL
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
    account_id UUID NOT NULL,
    category_id UUID NOT NULL,
    FOREIGN KEY (category_id) REFERENCES public.category(id)
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

CREATE TABLE public.shared_shopping_list (
    account_id UUID NOT NULL,
    shopping_list_id UUID NOT NULL,
    cost_factor INTEGER NOT NULL,
    PRIMARY KEY (shopping_list_id, account_id),
    FOREIGN KEY (shopping_list_id) REFERENCES public.shopping_list (id)
);