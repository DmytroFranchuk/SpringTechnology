package de.telran.SpringTechnologyBankApp.controllers.welcome;

import de.telran.SpringTechnologyBankApp.configurations.securityWeb.SecurityConfig;
import de.telran.SpringTechnologyBankApp.services.usersapp.interf.UserApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ControllerWelcome.class)
@Import({SecurityConfig.class})
class ControllerWelcomeTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserApplicationService userApplicationService;

//    @Test
//    @WithMockUser(username = "superUserApp", password = "1111", roles = "REGISTRAR")
//    @DisplayName("Страница приветствия")
//    void testWelcome() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Welcome to SpringTechnologyBankApp"));
//    }
}