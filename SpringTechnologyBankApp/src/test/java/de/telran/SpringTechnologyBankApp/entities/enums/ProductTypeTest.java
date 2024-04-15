package de.telran.SpringTechnologyBankApp.entities.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTypeTest {
    @Test
    void values() {
        ProductType[] values = ProductType.values();
        assertEquals(3, values.length);
        assertEquals(ProductType.CREDIT_ACCOUNT, values[0]);
        assertEquals(ProductType.DEBIT_ACCOUNT, values[1]);
        assertEquals(ProductType.SAVING_ACCOUNT, values[2]);
    }

    @Test
    void valueOf() {
        assertEquals(ProductType.CREDIT_ACCOUNT, ProductType.valueOf("CREDIT_ACCOUNT"));
        assertEquals(ProductType.DEBIT_ACCOUNT, ProductType.valueOf("DEBIT_ACCOUNT"));
        assertEquals(ProductType.SAVING_ACCOUNT, ProductType.valueOf("SAVING_ACCOUNT"));
    }
}