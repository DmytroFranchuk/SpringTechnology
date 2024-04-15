package de.telran.SpringTechnologyBankApp.entities.bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientTest {
    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
    }

    @Test
    void testAddAccount() {
        Account account = new Account();
        client.addAccount(account);
        Assertions.assertTrue(client.getAccounts().contains(account));
        Assertions.assertEquals(client, account.getClient());
    }

    @Test
    void testRemoveAccount() {
        Account account = new Account();
        client.addAccount(account);
        client.removeAccount(account);
        Assertions.assertFalse(client.getAccounts().contains(account));
        Assertions.assertNull(account.getClient());
    }

}