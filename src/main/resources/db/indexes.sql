-- BucketList
-- Tabela category
CREATE INDEX idx_category_account_id ON category(account_id);
CREATE INDEX idx_category_name_account_id ON category(name, account_id);

-- Tabela bucket_list
CREATE INDEX idx_bucket_list_account_id ON bucket_list(account_id);
CREATE INDEX idx_bucket_list_active_account_id ON bucket_list(account_id, active);

-- Tabela item
CREATE INDEX idx_item_bucket_list_id ON item(bucket_list_id);
CREATE INDEX idx_item_bucket_list_id_active ON item(bucket_list_id, active);

-- Tabela shared_bucket_list
CREATE INDEX idx_shared_bucket_list_account_id ON shared_bucket_list(account_id);
CREATE INDEX idx_shared_bucket_list_shopping_list_id ON shared_bucket_list(bucket_list_id);


-- ShoppingList
-- Tabela category
CREATE INDEX idx_category_account_id ON category(account_id);
CREATE INDEX idx_category_name_account_id ON category(name, account_id);

-- Tabela shopping_list
CREATE INDEX idx_shopping_list_account_id ON shopping_list(account_id);
CREATE INDEX idx_shopping_list_active_account_id ON shopping_list(account_id, active);

-- Tabela item
CREATE INDEX idx_item_shopping_list_id ON item(shopping_list_id);
CREATE INDEX idx_item_shopping_list_id_active ON item(shopping_list_id, active);

-- Tabela shared_shopping_list
CREATE INDEX idx_shared_shopping_list_account_id ON shared_shopping_list(account_id);
CREATE INDEX idx_shared_shopping_list_shopping_list_id ON shared_shopping_list(shopping_list_id);

-- Tabela expense_share
CREATE INDEX idx_expense_share_account_id ON expense_share(account_id);
CREATE INDEX idx_expense_share_account_id_reimbursed ON expense_share(account_id, reimbursed);

-- Tabela expense
CREATE INDEX idx_expense_shopping_list_id ON expense(shopping_list_id);


-- Task
-- Tabela task
CREATE INDEX idx_task_account_id ON task(account_id);
CREATE INDEX idx_task_account_id_is_deleted ON task(account_id, is_deleted);
CREATE INDEX idx_task_account_id_is_completed_is_deleted ON task(account_id, is_completed, is_deleted);

-- Tabela tag
CREATE INDEX idx_tag_account_id ON tag(account_id);
CREATE INDEX idx_tag_name_account_id ON tag(name, account_id);


-- Account
-- Tabela account_settings
CREATE INDEX idx_account_settings_account_id ON account_settings(account_id);


-- Keycloak
-- Tabela user_entity
CREATE INDEX idx_keycloak_user_email ON user_entity(email);