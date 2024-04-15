package de.telran.SpringTechnologyBankApp.controllers.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.SpringTechnologyBankApp.TestData;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.exceptions.NotExistEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.services.bank.impl.AccountServiceImpl;
import de.telran.SpringTechnologyBankApp.services.usersapp.interf.UserApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "superUserApp", roles = "REGISTRAR")
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AccountServiceImpl accountService;
    @MockBean
    private UserApplicationService userApplicationService;

    @Test
    void createAccount() throws Exception {
        when(accountService.createAccount(TestData.ACCOUNT_DTO)).thenReturn(TestData.ACCOUNT_DTO);
        mockMvc.perform(post("/api/v1/accounts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.ACCOUNT_DTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clientId").value(TestData.ACCOUNT_DTO.getClientId()))
                .andExpect(jsonPath("$.accountType").value(TestData.ACCOUNT_DTO.getAccountType().toString()));
    }

    @Test
    void testCreateAccount_Negative_MissingAgreements() throws Exception {
        when(accountService.createAccount(TestData.ACCOUNT_DTO)).thenThrow(NotExistEntityException.class);
        mockMvc.perform(post("/api/v1/accounts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.ACCOUNT_DTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateAccount_Negative_AgreementsNotFound() throws Exception {
        when(accountService.createAccount(TestData.ACCOUNT_DTO)).thenThrow(IllegalArgumentException.class);
        mockMvc.perform(post("/api/v1/accounts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.ACCOUNT_DTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAccountById() throws Exception {
        Long accountId = 1L;
        when(accountService.getAccountById(accountId)).thenReturn(TestData.ACCOUNT_DTO);
        mockMvc.perform(get("/api/v1/accounts/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountId));
    }

    @Test
    void testGetAccountById_Negative_AccountNotFound() throws Exception {
        Long nonExistentAccountId = 999L;
        when(accountService.getAccountById(nonExistentAccountId)).thenThrow(NotFoundEntityException.class);
        mockMvc.perform(get("/api/v1/accounts/{id}", nonExistentAccountId))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateAccountById() throws Exception {
        Long accountId = 1L;
        when(accountService.updateAccountById(accountId, TestData.ACCOUNT_DTO_UPDATING))
                .thenReturn(TestData.ACCOUNT_DTO_UPDATING);
        mockMvc.perform(put("/api/v1/accounts/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.ACCOUNT_DTO_UPDATING)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountId));
    }

    @Test
    void testUpdateAccountById_Negative_AccountNotFound() throws Exception {
        Long accountId = 1L;
        when(accountService.updateAccountById(accountId, TestData.ACCOUNT_DTO_UPDATING))
                .thenThrow(NotFoundEntityException.class);
        mockMvc.perform(put("/api/v1/accounts/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.ACCOUNT_DTO_UPDATING)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateAccountById_Negative_ClientNotFound() throws Exception {
        Long accountId = 1L;
        when(accountService.updateAccountById(accountId, TestData.ACCOUNT_DTO_UPDATING))
                .thenThrow(NotExistEntityException.class);
        mockMvc.perform(put("/api/v1/accounts/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.ACCOUNT_DTO_UPDATING)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateAccountByIdPatch() throws Exception {
        Long accountId = 1L;
        when(accountService.updateAccountById(accountId, TestData.ACCOUNT_DTO_UPDATING))
                .thenReturn(TestData.ACCOUNT_DTO_UPDATING);
        mockMvc.perform(patch("/api/v1/accounts/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.ACCOUNT_DTO_UPDATING)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountId));
    }

    @Test
    void testUpdateAccountByIdPatch_Negative_AccountNotFound() throws Exception {
        when(accountService.updateAccountById(999L, TestData.ACCOUNT_DTO_UPDATING))
                .thenThrow(NotFoundEntityException.class);
        mockMvc.perform(patch("/api/v1/accounts/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.ACCOUNT_DTO_UPDATING)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateAccountByIdPatch_Negative_ClientNotFound() throws Exception {
        Long id = 999L;
        when(accountService.updateAccountById(id, TestData.ACCOUNT_DTO_UPDATING))
                .thenThrow(NotExistEntityException.class);
        mockMvc.perform(patch("/api/v1/accounts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.ACCOUNT_DTO_UPDATING)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAccountById() throws Exception {
        doNothing().when(accountService).deleteAccountById(1L);
        mockMvc.perform(delete("/api/v1/accounts/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Счет с id " + 1L + " успешно удален"));
    }

    @Test
    void testDeleteAccountById_Negative_AccountNotFound() throws Exception {
        Long accountId = 1L;
        doThrow(NotFoundEntityException.class).when(accountService).deleteAccountById(accountId);
        mockMvc.perform(delete("/api/v1/accounts/{id}", accountId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAccountsByStatus() throws Exception {
        StatusType status = StatusType.ACTIVE;
        when(accountService.getAllAccountsWhereStatusTypeIs(status))
                .thenReturn(List.of(TestData.ACCOUNT_DTO));
        mockMvc.perform(get("/api/v1/accounts/status/{status}", status))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetAccountsByStatus_Negative_NoAccountsFound() throws Exception {
        StatusType status = StatusType.ACTIVE;
        when(accountService.getAllAccountsWhereStatusTypeIs(status)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/accounts/status/{status}", status))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void findAccountsByClientId() throws Exception {
        Long clientId = 1L;
        when(accountService.getAccountsByClientId(clientId))
                .thenReturn(List.of(TestData.ACCOUNT_DTO));
        mockMvc.perform(get("/api/v1/accounts/clients/{clientId}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testFindAccountsByClientId_Negative_ClientNotFound() throws Exception {
        Long clientId = 999L;
        when(accountService.getAccountsByClientId(clientId)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/accounts/clients/{clientId}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void findAccountsByManagerId() throws Exception {
        Long managerId = 1L;
        when(accountService.getAccountsByManagerId(managerId))
                .thenReturn(List.of(TestData.ACCOUNT_DTO));
        mockMvc.perform(get("/api/v1/accounts/managers/{managerId}", managerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testFindAccountsByManagerId_Negative_ManagerNotFound() throws Exception {
        Long managerId = 999L;
        when(accountService.getAccountsByManagerId(managerId)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/accounts/managers/{managerId}", managerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}