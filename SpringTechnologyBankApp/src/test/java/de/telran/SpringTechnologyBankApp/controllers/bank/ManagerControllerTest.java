package de.telran.SpringTechnologyBankApp.controllers.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.SpringTechnologyBankApp.TestData;
import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ManagerDto;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.services.bank.interf.ManagerService;
import de.telran.SpringTechnologyBankApp.services.usersapp.interf.UserApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

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
class ManagerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ManagerService managerService;
    @MockBean
    private UserApplicationService userApplicationService;

    @Test
    @WithMockUser(username = "superUserApp", roles = "REGISTRAR")
    void createManager() throws Exception {
//        when(managerService.createManager(any(ManagerDto.class))).thenReturn(TestData.MANAGER_DTO);
//
//        mockMvc.perform(post("/api/v1/managers/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(TestData.MANAGER_DTO)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("Test Manager"));
    }

    @Test
    void getManagerById() throws Exception {
        Long managerId = 1L;
        when(managerService.getManagerById(managerId)).thenReturn(TestData.MANAGER_DTO);

        mockMvc.perform(get("/api/v1/managers/{id}", managerId)).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(TestData.MANAGER_DTO.getId())).andReturn();
    }

    @Test
    void testGetManagerByInvalidId() throws Exception {
        Long invalidManagerId = -1L;

        when(managerService.getManagerById(invalidManagerId)).thenThrow(new NotFoundEntityException("Не найден менеджер с id: " + invalidManagerId));

        mockMvc.perform(get("/api/v1/managers/{id}", invalidManagerId)).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    void updateManagerById() throws Exception {
        Long managerId = 1L;

        when(managerService.updateManagerById(eq(managerId), any(ManagerDto.class))).thenReturn(TestData.MANAGER_DTO);

        mockMvc.perform(put("/api/v1/managers/{id}", managerId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(TestData.MANAGER_DTO))).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(TestData.MANAGER_DTO.getId())).andReturn();
    }

    @Test
    void testUpdateNonexistentManagerById() throws Exception {
        Long nonexistentManagerId = -1L;
        when(managerService.updateManagerById(eq(nonexistentManagerId), any(ManagerDto.class))).thenThrow(new NotFoundEntityException("Менеджер с id: " + nonexistentManagerId + " не найден"));

        mockMvc.perform(put("/api/v1/managers/{id}", nonexistentManagerId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(TestData.MANAGER_DTO))).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    void updateManagerByIdPatch() throws Exception {
        Long managerId = 1L;
        when(managerService.updateManagerById(eq(managerId), any(ManagerDto.class))).thenReturn(TestData.MANAGER_DTO);

        mockMvc.perform(patch("/api/v1/managers/{id}", managerId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(TestData.MANAGER_DTO))).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(TestData.MANAGER_DTO.getId())).andReturn();
    }

    @Test
    void testUpdateNonexistentManagerByIdPatch() throws Exception {
        Long nonexistentManagerId = -1L;
        doThrow(new NotFoundEntityException("Менеджер с id: " + nonexistentManagerId + " не найден")).when(managerService).updateManagerById(eq(nonexistentManagerId), any(ManagerDto.class));

        mockMvc.perform(patch("/api/v1/managers/{id}", nonexistentManagerId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(TestData.MANAGER_DTO))).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    void getManagersByStatus() throws Exception {
        StatusType status = StatusType.ACTIVE;
        when(managerService.getAllManagersWhereStatusTypeIs(eq(status))).thenReturn(List.of(TestData.MANAGER_DTO_FOR_BY_CONDITION));

        mockMvc.perform(get("/api/v1/managers/status/{status}", status).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$[0].email").value(TestData.MANAGER_DTO_FOR_BY_CONDITION.getEmail())).andReturn();
    }

    @Test
    void testGetManagersByNonexistentStatus() throws Exception {
        when(managerService.getAllManagersWhereStatusTypeIs(eq(StatusType.INACTIVE))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/managers/status/{status}", StatusType.INACTIVE).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$").isEmpty()).andReturn();
    }

    @Test
    void getAllManagersAfterDate() throws Exception {
        LocalDate date = LocalDate.now();
        when(managerService.getAllManagersCreatedAfterDate(eq(date.atStartOfDay()))).thenReturn(List.of(TestData.MANAGER_DTO_FOR_BY_CONDITION));

        mockMvc.perform(get("/api/v1/managers/createdAt").param("date", date.toString()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$[0].email").value(TestData.MANAGER_DTO_FOR_BY_CONDITION.getEmail())).andReturn();
    }

    @Test
    void testGetAllManagersAfterInvalidDate() throws Exception {
        LocalDate date = LocalDate.now();
        when(managerService.getAllManagersCreatedAfterDate(eq(date.atStartOfDay()))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/managers/createdAt").param("date", date.toString()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$").isEmpty()).andReturn();
    }
}