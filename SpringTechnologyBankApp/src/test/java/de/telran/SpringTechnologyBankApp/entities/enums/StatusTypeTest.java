package de.telran.SpringTechnologyBankApp.entities.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTypeTest {

    @Test
    @DisplayName("Тест статусов")
    void testStatusParsing() {
        String active = "ACTIVE";
        String pending = "PENDING";
        String removed = "REMOVED";
        String blocked = "BLOCKED";
        String inactive = "INACTIVE";
        String invalidStatus = "INVALID";

        assertEquals(StatusType.ACTIVE, StatusType.valueOf(active));
        assertEquals(StatusType.PENDING, StatusType.valueOf(pending));
        assertEquals(StatusType.REMOVED, StatusType.valueOf(removed));
        assertEquals(StatusType.BLOCKED, StatusType.valueOf(blocked));
        assertEquals(StatusType.INACTIVE, StatusType.valueOf(inactive));
        assertThrows(IllegalArgumentException.class, () -> StatusType.valueOf(invalidStatus));
    }
}