package de.telran.SpringTechnologyBankApp.entities.bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ManagerTest {
    private Manager manager;

    @BeforeEach
    void setUp() {
        manager = new Manager();
    }

    @Test
    void testAddClient() {
        Client client = new Client();
        manager.addClient(client);
        Assertions.assertTrue(manager.getClients().contains(client));
        Assertions.assertEquals(manager, client.getManager());
    }

    @Test
    void testRemoveClient() {
        Client client = new Client();
        manager.addClient(client);
        manager.removeClient(client);
        Assertions.assertFalse(manager.getClients().contains(client));
        Assertions.assertNull(client.getManager());
    }

    @Test
    void testAddProduct() {
        Product product = new Product();
        manager.addProduct(product);
        Assertions.assertTrue(manager.getProducts().contains(product));
        Assertions.assertEquals(manager, product.getManager());
    }

    @Test
    void testRemoveProduct() {
        Product product = new Product();
        manager.addProduct(product);
        manager.removeProduct(product);
        Assertions.assertFalse(manager.getProducts().contains(product));
        Assertions.assertNull(product.getManager());
    }
}