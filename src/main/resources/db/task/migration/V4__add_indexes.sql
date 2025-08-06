CREATE INDEX IF NOT EXISTS idx_task_account_id ON task(account_id);
CREATE INDEX IF NOT EXISTS idx_task_account_id_is_deleted ON task(account_id, is_deleted);
CREATE INDEX IF NOT EXISTS idx_task_account_id_is_completed_is_deleted ON task(account_id, is_completed, is_deleted);

CREATE INDEX IF NOT EXISTS idx_tag_account_id ON tag(account_id);
CREATE INDEX IF NOT EXISTS idx_tag_name_account_id ON tag(name, account_id);