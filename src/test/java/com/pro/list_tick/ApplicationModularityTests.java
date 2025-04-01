package com.pro.list_tick;

import com.tngtech.archunit.core.domain.JavaClass;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ApplicationModularityTests {
    static ApplicationModules modules = ApplicationModules.of(ListTickApplication.class,
            JavaClass.Predicates.resideInAPackage("com.pro.list_tick.event"));

    @Test
    void bootstrapApplicationModules() {
        modules.verify();
    }
}
