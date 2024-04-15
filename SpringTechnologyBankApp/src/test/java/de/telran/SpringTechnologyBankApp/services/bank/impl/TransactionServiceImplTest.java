package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.TestData;
import de.telran.SpringTechnologyBankApp.dtos.bank.transaction.TransactionDto;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.mappers.bank.TransactionMapper;
import de.telran.SpringTechnologyBankApp.repositories.bank.AccountRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.ClientRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private TransactionMapper transactionMapper;
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        Mockito.reset(transactionRepository);
        Mockito.reset(accountRepository);
        Mockito.reset(clientRepository);
    }

    @Test
    void getAllTransactionsByClientId() {
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(TestData.CLIENT));
        when(transactionRepository.findAllTransactionsByClientId(clientId)).thenReturn(List.of(TestData.TRANSACTION));
        when(transactionMapper.transactionToTransactionDto(TestData.TRANSACTION)).thenReturn(TestData.TRANSACTION_DTO);
        List<TransactionDto> actualTransactions = transactionService.getAllTransactionsByClientId(clientId);
        assertEquals(List.of(TestData.TRANSACTION_DTO), actualTransactions);
    }

    @Test
    void getAllTransactionsByClientId_Negative_ClientNotFound() {
        Long nonExistingClientId = 1000L;
        when(clientRepository.findById(nonExistingClientId)).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> transactionService.getAllTransactionsByClientId(nonExistingClientId));
    }

    @Test
    void getAllTransactionsByClientIdForLastMonth() {
        Long clientId = 1L;
        LocalDate startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDateTime startDateWithTime = startDate.atStartOfDay();
        when(transactionRepository.findAllTransactionsByClientIdForLastMonth(clientId, startDateWithTime))
                .thenReturn(List.of(TestData.TRANSACTION));
        when(transactionMapper.transactionToTransactionDto(TestData.TRANSACTION))
                .thenReturn(TestData.TRANSACTION_DTO);
        List<TransactionDto> actualTransactions = transactionService.getAllTransactionsByClientIdForLastMonth(clientId);
        assertEquals(List.of(TestData.TRANSACTION_DTO), actualTransactions);
    }

    @Test
    void getAllTransactionsByClientIdForLastMonth_Negative_NoTransactions() {
        Long clientId = 2L;
        LocalDate startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDateTime startDateWithTime = startDate.atStartOfDay();
        when(transactionRepository.findAllTransactionsByClientIdForLastMonth(clientId, startDateWithTime))
                .thenReturn(Collections.emptyList());
        List<TransactionDto> actualTransactions = transactionService.getAllTransactionsByClientIdForLastMonth(clientId);
        assertTrue(actualTransactions.isEmpty());
    }

    @Test
    void getAllTransactionsWithAmountGreaterThan() {
        when(transactionRepository.findAllTransactionsWithAmountGreaterThan(TestData.BIG_DECIMAL_SUM))
                .thenReturn(List.of(TestData.TRANSACTION));
        List<TransactionDto> actualTransactions = transactionService.getAllTransactionsWithAmountGreaterThan(TestData.BIG_DECIMAL_SUM);
        assertEquals(1, actualTransactions.size());
    }

    @Test
    void testGetAllTransactionsWithAmountGreaterThan_Negative_NoTransactions() {
        when(transactionRepository.findAllTransactionsWithAmountGreaterThan(TestData.BIG_DECIMAL_SUM))
                .thenReturn(Collections.emptyList());
        List<TransactionDto> actualTransactions = transactionService.getAllTransactionsWithAmountGreaterThan(TestData.BIG_DECIMAL_SUM);
        assertTrue(actualTransactions.isEmpty());
    }
}