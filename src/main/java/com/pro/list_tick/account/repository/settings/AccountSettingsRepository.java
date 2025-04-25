package com.pro.list_tick.account.repository.settings;

import com.pro.list_tick.account.model.settings.AccountSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountSettingsRepository extends JpaRepository<AccountSettings, UUID> {
}
