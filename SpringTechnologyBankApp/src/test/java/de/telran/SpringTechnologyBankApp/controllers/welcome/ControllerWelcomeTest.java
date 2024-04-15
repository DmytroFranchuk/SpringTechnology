package de.telran.SpringTechnologyBankApp.controllers.welcome;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerWelcomeTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Страница приветствия доступна анонимным пользователям")
    void testWelcomeForAnonymousUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to SpringTechnologyBankApp"));
    }

    @Test
    @WithMockUser(username = "superUserApp", roles = "REGISTRAR")
    @DisplayName("Страница приветствия доступна пользователю с ролью REGISTRAR")
    void testWelcomeForRegistrarUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to SpringTechnologyBankApp"));
    }

    @Test
    @WithMockUser(username = "manager", roles = "MANAGER")
    @DisplayName("Страница приветствия доступна пользователю с ролью MANAGER")
    void testWelcomeForManagerUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to SpringTechnologyBankApp"));
    }

    @Test
    @WithMockUser(username = "client", roles = "CLIENT")
    @DisplayName("Страница приветствия доступна пользователю с ролью CLIENT")
    void testWelcomeForClientUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to SpringTechnologyBankApp"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Страница приветствия доступна пользователю с ролью ADMIN")
    void testWelcomeForAdminUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to SpringTechnologyBankApp"));
    }
}