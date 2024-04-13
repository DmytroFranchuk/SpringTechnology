package de.telran.SpringTechnologyBankApp.services.bank.interf;

import de.telran.SpringTechnologyBankApp.dtos.bank.account.AccountDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Account;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto account);

    AccountDto getAccountById(Long id);

    AccountDto updateAccountById(Long id, AccountDto account);

    void deleteAccountById(Long id);

    List<AccountDto> getAllAccountsWhereStatusTypeIs(StatusType status);

    List<AccountDto> getAccountsByClientId(Long clientId);

    List<AccountDto> getAccountsByManagerId(Long managerId);
}
