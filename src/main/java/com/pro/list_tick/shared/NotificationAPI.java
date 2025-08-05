package com.pro.list_tick.shared;

import java.util.UUID;

import org.springframework.lang.Nullable;

public interface NotificationAPI {

  void create(@Nullable UUID objectId, @Nullable String objectClass, String description, UUID accountId);

}
