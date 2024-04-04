package de.telran.SpringTechnologyBankApp.services.bank.interf;

import de.telran.SpringTechnologyBankApp.dtos.bank.account.AccountDto;
import de.telran.SpringTechnologyBankApp.entities.enums.AccountType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    AccountDto createAccount(AccountDto account);

    Optional<AccountDto> getAccountById(Long id);

    AccountDto updateAccountById(Long id, AccountDto account);

    void deleteAccountById(Long id);

    List<AccountDto> getAllAccountsWhereStatusTypeIs(StatusType status);

    List<AccountDto> getAllActiveAccountsByClientId(Long clientId);

    List<AccountDto> getAllActiveAccountsByClientId(Long clientId, AccountType accountType, StatusType status);

}
