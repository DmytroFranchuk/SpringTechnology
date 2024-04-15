package de.telran.SpringTechnologyBankApp.entities.bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductTest {
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
    }

    @Test
    void testAddAgreement() {
        Agreement agreement = new Agreement();
        product.addAgreement(agreement);
        Assertions.assertTrue(product.getAgreements().contains(agreement));
        Assertions.assertEquals(product, agreement.getProduct());
    }

    @Test
    void testRemoveAgreement() {
        Agreement agreement = new Agreement();
        product.addAgreement(agreement);
        product.removeAgreement(agreement);
        Assertions.assertFalse(product.getAgreements().contains(agreement));
        Assertions.assertNull(agreement.getProduct());
    }
}