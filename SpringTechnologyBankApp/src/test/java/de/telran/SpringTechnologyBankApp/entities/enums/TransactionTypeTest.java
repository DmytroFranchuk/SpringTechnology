package de.telran.SpringTechnologyBankApp.entities.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTypeTest {
    @Test
    void values() {
        TransactionType[] values = TransactionType.values();
        assertEquals(4, values.length);
        assertEquals(TransactionType.TRANSFER, values[0]);
        assertEquals(TransactionType.PAYMENT, values[1]);
        assertEquals(TransactionType.CASH, values[2]);
        assertEquals(TransactionType.DEPOSIT, values[3]);
    }

    @Test
    void valueOf() {
        assertEquals(TransactionType.TRANSFER, TransactionType.valueOf("TRANSFER"));
        assertEquals(TransactionType.PAYMENT, TransactionType.valueOf("PAYMENT"));
        assertEquals(TransactionType.CASH, TransactionType.valueOf("CASH"));
        assertEquals(TransactionType.DEPOSIT, TransactionType.valueOf("DEPOSIT"));
    }
}