package de.telran.SpringTechnologyBankApp.mappers.bank;

import de.telran.SpringTechnologyBankApp.TestData;
import de.telran.SpringTechnologyBankApp.dtos.bank.account.AccountDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.client.AccountForClientDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AccountMapperTest {
    private AgreementMapper agreementMapper;
    private AccountMapper accountMapper;

    @BeforeEach
    void setUp() {
        agreementMapper = mock(AgreementMapper.class);
        accountMapper = Mappers.getMapper(AccountMapper.class);
    }

    @Test
    void accountDtoToAccount() {
        Account account = accountMapper.accountDtoToAccount(TestData.ACCOUNT_DTO);
        assertEquals(TestData.ACCOUNT_DTO.getBalance(), account.getBalance());
        assertEquals(TestData.ACCOUNT_DTO.getClientId(), account.getClient().getId());
        assertEquals(TestData.ACCOUNT_DTO.getAccountType(), account.getAccountType());
        assertEquals(TestData.ACCOUNT_DTO.getCurrencyCode(), account.getCurrencyCode());
        assertEquals(TestData.ACCOUNT_DTO.getStatusAccount(), account.getStatusAccount());
    }

    @Test
    void accountToAccountDto() {
        AccountDto accountDto = accountMapper.accountToAccountDto(TestData.ACCOUNT);
        assertEquals(TestData.ACCOUNT.getClient().getId(), accountDto.getClientId());
    }

    @Test
    void accountsToAccountDtos() {
        Set<Account> accounts = new HashSet<>(Arrays.asList(TestData.ACCOUNT, TestData.ACCOUNT_RECIPIENT));
        List<AccountDto> accountDtos = accountMapper.accountsToAccountDtos(new ArrayList<>(accounts));
        assertEquals(accounts.size(), accountDtos.size());
    }

    @Test
    void accountToAccountForClientDto() {
        AccountForClientDto accountForClientDto = accountMapper.accountToAccountForClientDto(TestData.ACCOUNT);
        assertEquals(TestData.ACCOUNT.getCurrencyCode(), accountForClientDto.getCurrencyCode());
    }
}