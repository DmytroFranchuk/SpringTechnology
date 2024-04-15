package de.telran.SpringTechnologyBankApp.entities.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyCodeTest {
    @Test
    void values() {
        CurrencyCode[] values = CurrencyCode.values();
        assertEquals(2, values.length);
        assertEquals(CurrencyCode.EUR, values[0]);
        assertEquals(CurrencyCode.USD, values[1]);
    }

    @Test
    void valueOf() {
        assertEquals(CurrencyCode.EUR, CurrencyCode.valueOf("EUR"));
        assertEquals(CurrencyCode.USD, CurrencyCode.valueOf("USD"));
    }
}