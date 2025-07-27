CREATE INDEX IF NOT EXISTS idx_category_account_id ON category(account_id);
CREATE INDEX IF NOT EXISTS idx_category_name_account_id ON category(name, account_id);

CREATE INDEX IF NOT EXISTS idx_shopping_list_account_id ON shopping_list(account_id);
CREATE INDEX IF NOT EXISTS idx_shopping_list_active_account_id ON shopping_list(account_id, active);

CREATE INDEX IF NOT EXISTS idx_item_shopping_list_id ON item(shopping_list_id);
CREATE INDEX IF NOT EXISTS idx_item_shopping_list_id_active ON item(shopping_list_id, active);

CREATE INDEX IF NOT EXISTS idx_shared_shopping_list_account_id ON shared_shopping_list(account_id);
CREATE INDEX IF NOT EXISTS idx_shared_shopping_list_shopping_list_id ON shared_shopping_list(shopping_list_id);

CREATE INDEX IF NOT EXISTS idx_expense_share_account_id ON expense_share(account_id);
CREATE INDEX IF NOT EXISTS idx_expense_share_account_id_reimbursed ON expense_share(account_id, reimbursed);

CREATE INDEX IF NOT EXISTS idx_expense_shopping_list_id ON expense(shopping_list_id);