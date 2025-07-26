CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS public.category (
     id UUID PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     colour VARCHAR(7) NOT NULL,
     account_id UUID NOT NULL
);

CREATE TABLE IF NOT EXISTS public.bucket_list (
     id UUID PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     active BOOLEAN NOT NULL,
     shared BOOLEAN NOT NULL,
     creation_date TIMESTAMP NOT NULL,
     account_id UUID NOT NULL,
     category_id UUID NOT NULL,
     FOREIGN KEY (category_id) REFERENCES public.category(id)
);

CREATE TABLE IF NOT EXISTS public.item (
     id UUID PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     value DECIMAL NULL,
     active BOOLEAN NOT NULL,
     bucket_list_id UUID,
     FOREIGN KEY (bucket_list_id) REFERENCES public.bucket_list(id)
);

CREATE TABLE IF NOT EXISTS public.shared_bucket_list (
     account_id UUID NOT NULL,
     bucket_list_id UUID NOT NULL,
     PRIMARY KEY (bucket_list_id, account_id),
     FOREIGN KEY (bucket_list_id) REFERENCES public.bucket_list(id)
);