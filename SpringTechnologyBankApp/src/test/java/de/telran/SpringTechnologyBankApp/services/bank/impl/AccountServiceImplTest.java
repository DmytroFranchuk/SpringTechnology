package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.TestData;
import de.telran.SpringTechnologyBankApp.dtos.bank.account.AccountDto;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.mappers.bank.AccountMapper;
import de.telran.SpringTechnologyBankApp.repositories.bank.AccountRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.AgreementRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AgreementRepository agreementRepository;
    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        Mockito.reset(clientRepository);
        Mockito.reset(accountRepository);
        Mockito.reset(agreementRepository);
    }

    @Test
    void createAccount() {
        when(clientRepository.findById(any())).thenReturn(Optional.of(TestData.CLIENT));
        when(agreementRepository.existsById(any())).thenReturn(true);
        when(accountRepository.save(any())).thenReturn(TestData.ACCOUNT);
        when(accountMapper.accountToAccountDto(any())).thenReturn(TestData.ACCOUNT_DTO);
        AccountDto result = accountService.createAccount(TestData.ACCOUNT_DTO);
        assertNotNull(result);
        assertEquals(TestData.ACCOUNT_DTO.getBalance(), result.getBalance());
    }

    @Test
    void createAccount_NegativeTest_ClientNotFound() {
        when(clientRepository.findById(any())).thenReturn(Optional.empty());
 assertThrows(RuntimeException.class, () -> accountService.createAccount(TestData.ACCOUNT_DTO));
    }
    @Test
    void getAccountById() {
       when(accountRepository.findById(1L)).thenReturn(Optional.of(TestData.ACCOUNT));
        when(accountMapper.accountToAccountDto(TestData.ACCOUNT)).thenReturn(TestData.ACCOUNT_DTO);
 AccountDto result = accountService.getAccountById(1L);
        assertNotNull(result);
        assertEquals(TestData.ACCOUNT_DTO.getId(), result.getId());
    }
    @Test
    void getAccountById_NegativeTest_AccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> accountService.getAccountById(1L));
    }
    @Test
    void updateAccountById() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(TestData.ACCOUNT_UPDATING));
        when(clientRepository.findById(2L)).thenReturn(Optional.of(TestData.CLIENT_2));
        when(agreementRepository.findById(TestData.AGREEMENT.getId())).thenReturn(Optional.of(TestData.AGREEMENT));
        when(accountRepository.save(TestData.ACCOUNT_UPDATING)).thenReturn(TestData.ACCOUNT_UPDATING);
        when(accountMapper.accountToAccountDto(TestData.ACCOUNT_UPDATING)).thenReturn(TestData.ACCOUNT_DTO_UPDATING);
        AccountDto result = accountService.updateAccountById(1L, TestData.ACCOUNT_DTO_UPDATING);
        assertNotNull(result);
        assertEquals(TestData.ACCOUNT_DTO_UPDATING.getId(), result.getId());
        assertEquals(TestData.ACCOUNT_DTO_UPDATING.getClientId(), result.getClientId());
    }

    @Test
    void updateAccountById_NegativeTest_AccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
assertThrows(NotFoundEntityException.class, () -> accountService.updateAccountById(1L, new AccountDto()));
    }
    @Test
    void deleteAccountById() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(TestData.ACCOUNT));
 assertDoesNotThrow(() -> accountService.deleteAccountById(1L));
        assertEquals(StatusType.REMOVED, TestData.ACCOUNT.getStatusAccount());
        verify(accountRepository, times(1)).save(TestData.ACCOUNT);
    }

    @Test
    void deleteAccountById_NegativeTest_AccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
  assertThrows(NotFoundEntityException.class, () -> accountService.deleteAccountById(1L));
        verify(accountRepository, never()).save(any());
    }
    @Test
    void getAllAccountsWhereStatusTypeIs() {
        when(accountRepository.findAccountsByStatusAccount(StatusType.ACTIVE)).thenReturn(List.of(TestData.ACCOUNT));
when(accountMapper.accountsToAccountDtos(List.of(TestData.ACCOUNT))).thenReturn(List.of(TestData.ACCOUNT_DTO));
 List<AccountDto> result = accountService.getAllAccountsWhereStatusTypeIs(StatusType.ACTIVE);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertIterableEquals(List.of(TestData.ACCOUNT_DTO), result);
    }

    @Test
    void getAllAccountsWhereStatusTypeIs_NegativeTest_EmptyList() {
        when(accountRepository.findAccountsByStatusAccount(StatusType.ACTIVE)).thenReturn(new ArrayList<>());
List<AccountDto> result = accountService.getAllAccountsWhereStatusTypeIs(StatusType.ACTIVE);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    @Test
    void getAccountsByClientId() {
        Long clientId = 1L;
        when(accountRepository.findAccountsByClientId(clientId)).thenReturn(List.of(TestData.ACCOUNT));
when(accountMapper.accountsToAccountDtos(List.of(TestData.ACCOUNT))).thenReturn(List.of(TestData.ACCOUNT_DTO));
List<AccountDto> result = accountService.getAccountsByClientId(clientId);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(List.of(TestData.ACCOUNT_DTO), result);
}
    @Test
    void getAccountsByClientId_NegativeTest_EmptyList() {
        Long clientId = 1L;
 when(accountRepository.findAccountsByClientId(clientId)).thenReturn(new ArrayList<>());
        List<AccountDto> result = accountService.getAccountsByClientId(clientId);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    @Test
    void getAccountsByManagerId() {
        Long managerId = 1L;
        when(accountRepository.findAccountsByManagerId(managerId)).thenReturn(List.of(TestData.ACCOUNT));
        when(accountMapper.accountsToAccountDtos(List.of(TestData.ACCOUNT))).thenReturn(List.of(TestData.ACCOUNT_DTO));
List<AccountDto> result = accountService.getAccountsByManagerId(managerId);
assertNotNull(result);
        assertEquals(1, result.size());
        assertIterableEquals(List.of(TestData.ACCOUNT_DTO), result);
    }

    @Test
    void getAccountsByManagerId_NegativeTest_EmptyList() {
        Long managerId = 1L;
when(accountRepository.findAccountsByManagerId(managerId)).thenReturn(new ArrayList<>());
        List<AccountDto> result = accountService.getAccountsByManagerId(managerId);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}