package de.telran.SpringTechnologyBankApp.entities.bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class AccountTest {
    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    @Test
    void testAddAgreement() {
        Agreement agreement = new Agreement();
        account.addAgreement(agreement);
        Assertions.assertTrue(account.getAgreements().contains(agreement));
        Assertions.assertTrue(agreement.getAccounts().contains(account));
    }

    @Test
    void testRemoveAgreement() {
        Agreement agreement = new Agreement();
        account.addAgreement(agreement);
        account.removeAgreement(agreement);
        Assertions.assertFalse(account.getAgreements().contains(agreement));
        Assertions.assertFalse(agreement.getAccounts().contains(account));
    }

    @Test
    void testAddDebitAccount() {
        Transaction transaction = new Transaction();
        account.addDebitAccount(transaction);
        Assertions.assertTrue(account.getDebitTransactions().contains(transaction));
        Assertions.assertEquals(account, transaction.getDebitAccount());
    }

    @Test
    void testRemoveDebitAccount() {
        Transaction transaction = new Transaction();
        account.addDebitAccount(transaction);
        account.removeDebitAccount(transaction);
        Assertions.assertFalse(account.getDebitTransactions().contains(transaction));
        Assertions.assertNull(transaction.getDebitAccount());
    }

    @Test
    void testAddCreditAccount() {
        Transaction transaction = new Transaction();
        account.addCreditAccount(transaction);
        Assertions.assertTrue(account.getCreditTransactions().contains(transaction));
        Assertions.assertEquals(account, transaction.getCreditAccount());
    }

    @Test
    void testRemoveCreditAccount() {
        Transaction transaction = new Transaction();
        account.addCreditAccount(transaction);
        account.removeCreditAccount(transaction);
        Assertions.assertFalse(account.getCreditTransactions().contains(transaction));
        Assertions.assertNull(transaction.getCreditAccount());
    }
}