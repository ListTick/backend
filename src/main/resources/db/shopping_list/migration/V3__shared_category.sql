ALTER TABLE public.category
ALTER COLUMN account_id DROP NOT NULL;

INSERT INTO public.category (id, name, colour)
VALUES ('11111111-1111-1111-1111-111111111111', 'shared', '#3f454b');