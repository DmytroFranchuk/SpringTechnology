package de.telran.SpringTechnologyBankApp.entities.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTypeTest {
    @Test
    void values() {
        RoleType[] values = RoleType.values();
        assertEquals(5, values.length);
        assertEquals(RoleType.ROLE_ANONYMOUS, values[0]);
        assertEquals(RoleType.ROLE_CLIENT, values[1]);
        assertEquals(RoleType.ROLE_MANAGER, values[2]);
        assertEquals(RoleType.ROLE_ADMIN, values[3]);
        assertEquals(RoleType.ROLE_REGISTRAR, values[4]);
    }

    @Test
    void valueOf() {
        assertEquals(RoleType.ROLE_ANONYMOUS, RoleType.valueOf("ROLE_ANONYMOUS"));
        assertEquals(RoleType.ROLE_CLIENT, RoleType.valueOf("ROLE_CLIENT"));
        assertEquals(RoleType.ROLE_MANAGER, RoleType.valueOf("ROLE_MANAGER"));
        assertEquals(RoleType.ROLE_ADMIN, RoleType.valueOf("ROLE_ADMIN"));
        assertEquals(RoleType.ROLE_REGISTRAR, RoleType.valueOf("ROLE_REGISTRAR"));
    }
}