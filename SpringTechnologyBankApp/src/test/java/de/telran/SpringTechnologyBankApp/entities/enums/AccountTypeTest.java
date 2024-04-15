package de.telran.SpringTechnologyBankApp.entities.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTypeTest {
    @Test
    void values() {
        AccountType[] values = AccountType.values();
        assertEquals(2, values.length);
        assertEquals(AccountType.DEBIT, values[0]);
        assertEquals(AccountType.CREDIT, values[1]);
    }

    @Test
    void valueOf() {
        assertEquals(AccountType.DEBIT, AccountType.valueOf("DEBIT"));
        assertEquals(AccountType.CREDIT, AccountType.valueOf("CREDIT"));
    }
}