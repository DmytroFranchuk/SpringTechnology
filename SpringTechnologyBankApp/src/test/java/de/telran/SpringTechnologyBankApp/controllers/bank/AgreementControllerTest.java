package de.telran.SpringTechnologyBankApp.controllers.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.SpringTechnologyBankApp.TestData;
import de.telran.SpringTechnologyBankApp.dtos.bank.agreement.AgreementDto;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.services.bank.impl.AgreementServiceImpl;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "superUserApp", roles = "REGISTRAR")
class AgreementControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AgreementServiceImpl agreementService;
    @MockBean
    private UserApplicationService userApplicationService;

    @Test
    void createAgreement() throws Exception {
        when(agreementService.createAgreement(TestData.AGREEMENT_DTO))
                .thenReturn(TestData.AGREEMENT_DTO);
        mockMvc.perform(post("/api/v1/agreements/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.AGREEMENT_DTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void getAgreementById() throws Exception {
        when(agreementService.getAgreementById(1L)).thenReturn(TestData.AGREEMENT_DTO);
        mockMvc.perform(get("/api/v1/agreements/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TestData.AGREEMENT_DTO.getId()));
    }

    @Test
    void updateAgreementById() throws Exception {
        Long agreementId = 1L;
        when(agreementService.updateAgreementById(eq(agreementId), any(AgreementDto.class)))
                .thenReturn(TestData.AGREEMENT_DTO);
        mockMvc.perform(put("/api/v1/agreements/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.AGREEMENT_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.interestRate")
                        .value(TestData.AGREEMENT_DTO.getInterestRate()));
    }

    @Test
    void testUpdateAgreementById_Negative_NotFound() throws Exception {
        Long agreementId = 999L;
        when(agreementService.updateAgreementById(eq(agreementId), any(AgreementDto.class)))
                .thenThrow(NotFoundEntityException.class);
        mockMvc.perform(put("/api/v1/agreements/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.AGREEMENT_DTO)))
                .andExpect(status().isOk());
    }

    @Test
    void updateAgreementByIdPatch() throws Exception {
        when(agreementService.updateAgreementById(eq(1L), any(AgreementDto.class)))
                .thenReturn(TestData.AGREEMENT_DTO);
        mockMvc.perform(patch("/api/v1/agreements/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.AGREEMENT_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.interestRate")
                        .value(TestData.AGREEMENT_DTO.getInterestRate()));
    }

    @Test
    void testUpdateAgreementByIdPatch_Negative_AgreementNotFound() throws Exception {
        Long agreementId = 999L;
        when(agreementService.updateAgreementById(eq(agreementId), any(AgreementDto.class)))
                .thenThrow(NotFoundEntityException.class);

        mockMvc.perform(patch("/api/v1/agreements/{id}", agreementId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.AGREEMENT_DTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAgreementById() throws Exception {
        Long agreementId = 1L;

        mockMvc.perform(delete("/api/v1/agreements/{id}", agreementId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                .value("Договор с id " + agreementId + " успешно удален"));
        verify(agreementService, times(1)).deleteAgreementById(eq(agreementId));
    }

    @Test
    void testDeleteAgreementById_Negative_AgreementNotFound() throws Exception {
        Long agreementId = 999L;
        doThrow(NotFoundEntityException.class)
                .when(agreementService).deleteAgreementById(eq(agreementId));

        mockMvc.perform(delete("/api/v1/agreements/{id}", agreementId))
                .andExpect(status().isNotFound());
        verify(agreementService, times(1)).deleteAgreementById(eq(agreementId));
    }

    @Test
    void getAllAgreementsByStatus() throws Exception {
        StatusType status = StatusType.ACTIVE;
        when(agreementService.getAllAgreementsWhereStatusTypeIs(status))
                .thenReturn(List.of(TestData.AGREEMENT_DTO));
        mockMvc.perform(get("/api/v1/agreements/status/{status}", status)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        verify(agreementService, times(1))
                .getAllAgreementsWhereStatusTypeIs(eq(status));
    }

    @Test
    void testGetAllAgreementsByStatus_Negative_EmptyList() throws Exception {
        StatusType status = StatusType.ACTIVE;
        when(agreementService.getAllAgreementsWhereStatusTypeIs(status))
                .thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/agreements/status/{status}", status)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
        verify(agreementService, times(1))
                .getAllAgreementsWhereStatusTypeIs(eq(status));
    }

    @Test
    void getAgreementsByProductType() throws Exception {
        ProductType productType = ProductType.DEBIT_ACCOUNT;
        when(agreementService.findAgreementsByProductType(productType))
                .thenReturn(List.of(TestData.AGREEMENT_DTO));
        mockMvc.perform(get("/api/v1/agreements/products/{productType}", productType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(TestData.ACCOUNT_DTO.getId()));
    }

    @Test
    void testGetAgreementsByProductType_Negative_NoAgreements() throws Exception {
        ProductType productType = ProductType.SAVING_ACCOUNT;
        when(agreementService.findAgreementsByProductType(productType))
                .thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/agreements/products/{productType}", productType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}