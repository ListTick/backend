ALTER TABLE expense
    ALTER COLUMN amount TYPE numeric(15,2)
        USING amount::numeric(15,2);