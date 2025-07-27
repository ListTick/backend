CREATE INDEX idx_category_account_id ON category(account_id);
CREATE INDEX idx_category_name_account_id ON category(name, account_id);

CREATE INDEX idx_bucket_list_account_id ON bucket_list(account_id);
CREATE INDEX idx_bucket_list_active_account_id ON bucket_list(account_id, active);

CREATE INDEX idx_item_bucket_list_id ON item(bucket_list_id);
CREATE INDEX idx_item_bucket_list_id_active ON item(bucket_list_id, active);

CREATE INDEX idx_shared_bucket_list_account_id ON shared_bucket_list(account_id);
CREATE INDEX idx_shared_bucket_list_shopping_list_id ON shared_bucket_list(bucket_list_id);