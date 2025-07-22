CREATE TABLE expense_share (
    id UUID PRIMARY KEY,
    amount MONEY NOT NULL,
    currency VARCHAR(3) NOT NULL,
    reimbursed BOOLEAN NOT NULL,
    account_id UUID NOT NULL,
    expense_id UUID,
    CONSTRAINT fk_expense FOREIGN KEY (expense_id) REFERENCES expense(id)
);
