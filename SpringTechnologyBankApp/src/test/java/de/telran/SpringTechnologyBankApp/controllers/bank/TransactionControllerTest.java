package de.telran.SpringTechnologyBankApp.controllers.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.SpringTechnologyBankApp.TestData;
import de.telran.SpringTechnologyBankApp.dtos.bank.transaction.TransactionDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.transaction.TransactionResponseDto;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.repositories.bank.AccountRepository;
import de.telran.SpringTechnologyBankApp.services.bank.impl.TransactionServiceImpl;
import de.telran.SpringTechnologyBankApp.services.usersapp.interf.UserApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "superUserApp", roles = "REGISTRAR")
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private TransactionServiceImpl transactionService;
    @MockBean
    private UserApplicationService userApplicationService;

    @Test
    void createTransaction() throws Exception {
        when(accountRepository.findByIdAndStatusAccount(1L, StatusType.ACTIVE)).thenReturn(TestData.ACCOUNT);
        when(accountRepository.findByIdAndStatusAccount(2L, StatusType.ACTIVE)).thenReturn(TestData.ACCOUNT_RECIPIENT);
        TransactionResponseDto createdTransaction = new TransactionResponseDto();
        when(transactionService.createTransaction(any(TransactionDto.class))).thenReturn(createdTransaction);
        mockMvc.perform(post("/api/v1/transactions/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.TRANSACTION_DTO)))
                .andExpect(status().isOk());
    }
    
}
