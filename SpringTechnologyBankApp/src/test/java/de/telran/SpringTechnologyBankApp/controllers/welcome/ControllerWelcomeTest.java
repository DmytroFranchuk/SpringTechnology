package de.telran.SpringTechnologyBankApp.controllers.welcome;

import de.telran.SpringTechnologyBankApp.SpringTechnologyBankAppApplication;
import de.telran.SpringTechnologyBankApp.configurations.securityWeb.ConfigSecurityWeb;
import de.telran.SpringTechnologyBankApp.configurations.securityWeb.DefaultUsersConfig;
import de.telran.SpringTechnologyBankApp.configurations.securityWeb.PathExcludeSecurityConfig;
import de.telran.SpringTechnologyBankApp.repositories.usersapp.RoleUserApplicationRepository;
import de.telran.SpringTechnologyBankApp.repositories.usersapp.UserApplicationRepository;
import de.telran.SpringTechnologyBankApp.services.usersapp.impl.UserApplicationServiceImpl;
import de.telran.SpringTechnologyBankApp.services.usersapp.interf.UserApplicationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ControllerWelcome.class)
@Import({PathExcludeSecurityConfig.class})
class ControllerWelcomeTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserApplicationService userApplicationService;

    @Test
    @WithMockUser(username = "superUserApp", password = "1111", roles = "REGISTRAR")
    @DisplayName("Страница приветствия")
    void testWelcome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to SpringTechnologyBankApp"));
    }
}