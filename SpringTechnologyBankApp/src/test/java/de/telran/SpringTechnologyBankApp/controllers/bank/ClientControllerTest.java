package de.telran.SpringTechnologyBankApp.controllers.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.SpringTechnologyBankApp.TestData;
import de.telran.SpringTechnologyBankApp.dtos.bank.client.ClientDto;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.services.bank.impl.ClientServiceImpl;
import de.telran.SpringTechnologyBankApp.services.usersapp.interf.UserApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "superUserApp", roles = "REGISTRAR")
class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ClientServiceImpl clientService;
    @MockBean
    private UserApplicationService userApplicationService;

    @Test
    void createClient() throws Exception {
        when(clientService.createClient(eq(TestData.CLIENT_DTO))).thenReturn(TestData.CLIENT_DTO);
        mockMvc.perform(post("/api/v1/clients/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.CLIENT_DTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value(TestData.CLIENT_DTO.getEmail()));
    }

    @Test
    void testCreateClientWithInvalidData() throws Exception {
        when(clientService.createClient(eq(TestData.CLIENT_DTO)))
                .thenThrow(new DataIntegrityViolationException("Duplicate entry 'test@example.com' for key 'clients_email_unique'"));
        mockMvc.perform(post("/api/v1/clients/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.CLIENT_DTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getClientById() throws Exception {
        Long clientId = 1L;
        when(clientService.getClientById(eq(clientId))).thenReturn(TestData.CLIENT_DTO);
        mockMvc.perform(get("/api/v1/clients/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(TestData.CLIENT_DTO.getEmail()));
    }

    @Test
    public void getClientById_Negative_NotFound() throws Exception {
        Long clientId = 1L;
        when(clientService.getClientById(eq(clientId)))
                .thenThrow(new NotFoundEntityException("Не найден клиент с id: " + clientId));
        mockMvc.perform(get("/api/v1/clients/{id}", clientId))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateClientById() throws Exception {
        Long clientId = 1L;
        when(clientService.updateClientById(eq(clientId), any(ClientDto.class)))
                .thenReturn(TestData.CLIENT_DTO);
        mockMvc.perform(put("/api/v1/clients/{id}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.CLIENT_DTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateClientById_ClientNotFound() throws Exception {
        Long clientId = 1L;
        when(clientService.updateClientById(eq(clientId), any(ClientDto.class)))
                .thenThrow(new NotFoundEntityException("Клиент с id: " + clientId + " не найден"));
        mockMvc.perform(put("/api/v1/clients/{id}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.CLIENT_DTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateClientByIdPatch() throws Exception {
        Long clientId = 1L;
        when(clientService.updateClientById(eq(clientId), any(ClientDto.class)))
                .thenReturn(TestData.CLIENT_DTO);
        mockMvc.perform(patch("/api/v1/clients/{id}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.CLIENT_DTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateClientByIdPatch_ClientNotFound() throws Exception {
        Long clientId = 1L;
        when(clientService.updateClientById(eq(clientId), any(ClientDto.class)))
                .thenThrow(new NotFoundEntityException("Клиент с id: " + clientId + " не найден"));
        mockMvc.perform(patch("/api/v1/clients/{id}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.CLIENT_DTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteClientById() throws Exception {
        Long clientId = 1L;
        mockMvc.perform(delete("/api/v1/clients/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(String.format("Клиент с id %d успешно удален", clientId)));
    }

    @Test
    void testDeleteClientById_ClientNotFound() throws Exception {
        Long clientId = 1L;
        doThrow(new NotFoundEntityException("Клиент с id: " + clientId + " не найден"))
                .when(clientService).deleteClientById(clientId);
        mockMvc.perform(delete("/api/v1/clients/{id}", clientId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllClientsByStatus() throws Exception {
        when(clientService.getAllClientsWhereStatusTypeIs(eq(StatusType.ACTIVE)))
                .thenReturn(List.of(TestData.CLIENT_DTO));
        mockMvc.perform(get("/api/v1/clients/status/{status}", StatusType.ACTIVE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email").value(TestData.CLIENT_DTO.getEmail()));
    }

    @Test
    void testGetAllClientsByStatus_EmptyList() throws Exception {
        StatusType status = StatusType.ACTIVE;
        List<ClientDto> emptyList = Collections.emptyList();
        when(clientService.getAllClientsWhereStatusTypeIs(eq(status))).thenReturn(emptyList);
        mockMvc.perform(get("/api/v1/clients/status/{status}", status))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void getClientsWithBalanceMoreThan() throws Exception {
        BigDecimal balance = BigDecimal.valueOf(100);
        when(clientService.getAllClientsWhereBalanceMoreThan(eq(balance)))
                .thenReturn(List.of(TestData.CLIENT_DTO));
        mockMvc.perform(get("/api/v1/clients/with-more")
                        .param("balance", balance.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email").value(TestData.CLIENT_DTO.getEmail()));
    }

    @Test
    void testGetClientsWithBalanceMoreThan_EmptyList() throws Exception {
        BigDecimal balance = BigDecimal.valueOf(100);
        when(clientService.getAllClientsWhereBalanceMoreThan(eq(balance))).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/clients/with-more")
                        .param("balance", balance.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }
}