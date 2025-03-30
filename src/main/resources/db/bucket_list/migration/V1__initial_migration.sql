CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public.account (
     id UUID PRIMARY KEY,
     roles TEXT[] NOT NULL,
     last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE public.category (
     id UUID PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     colour VARCHAR(9) NOT NULL,
     account_id UUID,
     FOREIGN KEY (account_id) REFERENCES public.account(id)
);

CREATE TABLE public.bucket_list (
     id UUID PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     active BOOLEAN NOT NULL,
     creation_date TIMESTAMP NOT NULL,
     category_id UUID NOT NULL,
     account_id UUID NOT NULL,
     FOREIGN KEY (category_id) REFERENCES public.category(id),
     FOREIGN KEY (account_id) REFERENCES public.account(id)
);

CREATE TABLE public.item (
     id UUID PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     active BOOLEAN NOT NULL,
     bucket_list_id UUID,
     FOREIGN KEY (bucket_list_id) REFERENCES public.bucket_list(id)
);

CREATE TABLE public.account_bucket_list (
     bucket_list_id UUID NOT NULL,
     account_id UUID NOT NULL,
     PRIMARY KEY (bucket_list_id, account_id),
     FOREIGN KEY (bucket_list_id) REFERENCES public.bucket_list(id),
     FOREIGN KEY (account_id) REFERENCES public.account(id)
);