package com.pro.list_tick.shared.api;

import java.util.Optional;
import java.util.UUID;

public interface AccountAPI {

    UUID findIdByEmail(String email);

}
