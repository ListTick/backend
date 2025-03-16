package com.pro.list_tick;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ApplicationModularityTests {
    static ApplicationModules modules = ApplicationModules.of(ListTickApplication.class);

    @Test
    void bootstrapApplicationModules() {
        modules.verify();
    }
}
