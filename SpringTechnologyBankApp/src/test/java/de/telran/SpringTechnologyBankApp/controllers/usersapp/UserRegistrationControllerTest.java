package de.telran.SpringTechnologyBankApp.controllers.usersapp;

import de.telran.SpringTechnologyBankApp.configurations.securityWeb.SecurityConfig;
import de.telran.SpringTechnologyBankApp.controllers.registrationusers.UserRegistrationController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(UserRegistrationController.class)
@Import(SecurityConfig.class)
class UserRegistrationControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserApplicationServiceImpl userApplicationService;
//
//    @Test
//    @WithMockUser(username = "superUserApp", password = "1111", roles = "REGISTRAR")
//    public void testAddNewUserSuccess() throws Exception {
////        String userDtoJson = "{" +
////                "\"login\":\"testUser\"," +
////                "\"password\":\"password\"," +
////                "\"role\":\"ROLE_CLIENT\"," +
////                "\"host\":\"localhost:1975\"," +
////                "\"contentLength\":2024}";
////        doNothing().when(userApplicationService).addUser(any());
////        mockMvc.perform(MockMvcRequestBuilders.post("/registration/addUser")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(userDtoJson))
////                .andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(MockMvcResultMatchers.content().string("User added successfully"));
//    }
//
//    @Test
//    @WithMockUser(username = "superUserApp", password = "1111", roles = "REGISTRAR")
//    public void testAddNewUserWithEmptyOrNullLoginOrPassword() throws Exception {
////        String userDtoJson = "{" +
////                "\"login\":\"testUser\"," +
////                "\"password\":\"\"," +
////                "\"role\":\"ROLE_CLIENT\"," +
////                "\"host\":\"localhost:1975\"," +
////                "\"contentLength\":2024}";
////        doNothing().when(userApplicationService).addUser(any());
////        mockMvc.perform(MockMvcRequestBuilders.post("/registration/addUser")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(userDtoJson))
////                .andExpect(MockMvcResultMatchers.status().isBadRequest())
////                .andExpect(MockMvcResultMatchers.content().string("Login and password are required fields"));
//    }
//
////    @Test
////    @WithMockUser(username = "superUserApp", password = "1111", roles = "REGISTRAR")
////    public void testUserRoleValidationNullRole() throws Exception {
////        String userDtoJson = "{" +
////                "\"login\":\"testUser\"," +
////                "\"password\":\"password\"," +
////                "\"\":\"ROLE_CLIENT\"," +
////                "\"host\":\"localhost:1975\"," +
////                "\"contentLength\":2024}";
////        doNothing().when(userApplicationService).addUser(any());
////        mockMvc.perform(MockMvcRequestBuilders.post("/registration/addUser")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(userDtoJson))
////                .andExpect(MockMvcResultMatchers.status().isBadRequest())
////                .andExpect(MockMvcResultMatchers.content().string("Invalid role type: " + "null"));
//    }
//
////    @Test
////    @WithMockUser(username = "superUserApp", password = "1111", roles = "REGISTRAR")
////    public void testAddUser_Failure() {
////        String login = "testUser";
////        String password = "password";
////        RoleType roleUser = RoleType.ROLE_CLIENT;
////        String host = "localhost:1975";
////        int contentLength = 2024;
////        UserApplicationDto userDto = new UserApplicationDto(login, password, roleUser, host, contentLength);
////
////        UserApplicationServiceImpl userApplicationService = mock(UserApplicationServiceImpl.class);
////        doThrow(new RuntimeException("Failed to add user")).when(userApplicationService).addUser(any(UserApplicationDto.class));
////
////        HttpServletRequest request = mock(HttpServletRequest.class);
////        UserRegistrationController userRegistrationController = new UserRegistrationController(userApplicationService, request);
////        ResponseEntity<String> response = userRegistrationController.addNewUser(userDto);
////
////        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
////        assertEquals("Failed to add user", response.getBody());
////    }
//
////    @Test
////    @WithMockUser(username = "superUserApp", password = "1111", roles = "REGISTRAR")
////    public void testAddNewUserWithInvalidRole() throws Exception {
////        String userDtoJson = "{" +
////                "\"login\":\"testUser\"," +
////                "\"password\":\"password\"," +
////                "\"role\":\"INVALID_ROLE\"," +
////                "\"host\":\"localhost:1975\"," +
////                "\"contentLength\":2024}";
////        mockMvc.perform(MockMvcRequestBuilders.post("/registration/addUser")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(userDtoJson))
////                .andExpect(MockMvcResultMatchers.status().isBadRequest());
////    }
}