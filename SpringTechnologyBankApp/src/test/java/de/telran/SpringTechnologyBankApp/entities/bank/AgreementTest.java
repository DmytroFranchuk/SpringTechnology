package de.telran.SpringTechnologyBankApp.entities.bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AgreementTest {
    private Agreement agreement;

    @BeforeEach
    void setUp() {
        agreement = new Agreement();
    }

    @Test
    void testAddAccount() {
        Account account = new Account();
        agreement.addAccount(account);
        Assertions.assertTrue(agreement.getAccounts().contains(account));
        Assertions.assertTrue(account.getAgreements().contains(agreement));
    }

    @Test
    void testRemoveAccount() {
        Account account = new Account();
        agreement.addAccount(account);
        agreement.removeAccount(account);
        Assertions.assertFalse(agreement.getAccounts().contains(account));
        Assertions.assertFalse(account.getAgreements().contains(agreement));
    }
}