package de.telran.SpringTechnologyBankApp.entities;

import de.telran.SpringTechnologyBankApp.entities.bank.Manager;
import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    @Test
    void testLombokAnnotations() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Constructor<Manager> managerConstructor = Manager.class.getDeclaredConstructor();
        managerConstructor.setAccessible(true);
        Manager manager = managerConstructor.newInstance();

        Field idField = Manager.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(manager, 1L);

        Field firstNameField = Manager.class.getDeclaredField("firstName");
        firstNameField.setAccessible(true);
        firstNameField.set(manager, "John");

        Field lastNameField = Manager.class.getDeclaredField("lastName");
        lastNameField.setAccessible(true);
        lastNameField.set(manager, "Doe");

        Field passwordField = Manager.class.getDeclaredField("password");
        passwordField.setAccessible(true);
        passwordField.set(manager, "1234");

        Field emailField = Manager.class.getDeclaredField("email");
        emailField.setAccessible(true);
        emailField.set(manager, "john.doe@technologybank.com");

        Field descriptionField = Manager.class.getDeclaredField("description");
        descriptionField.setAccessible(true);
        descriptionField.set(manager, "Description");

        Field roleTypeField = Manager.class.getDeclaredField("roleType");
        roleTypeField.setAccessible(true);
        roleTypeField.set(manager, RoleType.ROLE_MANAGER);

        Field statusTypeField = Manager.class.getDeclaredField("statusType");
        statusTypeField.setAccessible(true);
        statusTypeField.set(manager, StatusType.ACTIVE);

        Field createdAtField = Manager.class.getDeclaredField("createdAt");
        createdAtField.setAccessible(true);
        createdAtField.set(manager, LocalDateTime.of(2003, 3, 15, 9, 32, 0));

        Field updatedAtField = Manager.class.getDeclaredField("updatedAt");
        updatedAtField.setAccessible(true);
        LocalDateTime nowDate = LocalDateTime.now();
        updatedAtField.set(manager, nowDate);

        assertNotNull(manager);
        assertEquals(1L, manager.getId());
        assertEquals("John", manager.getFirstName());
        assertEquals("Doe", manager.getLastName());
        assertEquals("1234", manager.getPassword());
        assertEquals("john.doe@technologybank.com", manager.getEmail());
        assertEquals("Description", manager.getDescription());
        assertEquals(RoleType.ROLE_MANAGER, manager.getRoleType());
        assertEquals(StatusType.ACTIVE, manager.getStatusType());
        assertEquals(LocalDateTime.of(2003, 3, 15, 9, 32, 0), manager.getCreatedAt());
        assertEquals(nowDate, manager.getUpdatedAt());
    }


}