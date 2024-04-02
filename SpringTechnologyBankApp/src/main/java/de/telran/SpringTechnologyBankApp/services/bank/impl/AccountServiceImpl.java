package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.dtos.bank.AccountDto;
import de.telran.SpringTechnologyBankApp.entities.enums.AccountType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.services.bank.interf.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Override
    public AccountDto createAccount(AccountDto account) {
        return null;
    }

    @Override
    public Optional<AccountDto> getAccountById(Long id) {
        return Optional.empty();
    }

    @Override
    public AccountDto updateAccountById(Long id, AccountDto account) {
        return null;
    }

    @Override
    public void deleteAccountById(Long id) {

    }

    @Override
    public List<AccountDto> getAllAccountsWhereStatusTypeIs(StatusType status) {
        return null;
    }

    @Override
    public List<AccountDto> getAllActiveAccountsByClientId(Long clientId) {
        return null;
    }

    @Override
    public List<AccountDto> getAllActiveAccountsByClientId(Long clientId, AccountType accountType, StatusType status) {
        return null;
    }
}
