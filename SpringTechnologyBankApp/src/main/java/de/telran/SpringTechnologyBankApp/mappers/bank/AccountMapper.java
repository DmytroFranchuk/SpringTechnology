package de.telran.SpringTechnologyBankApp.mappers.bank;

import de.telran.SpringTechnologyBankApp.dtos.bank.account.AccountDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.agreement.AgreementDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.client.AccountForClientDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.product.AgreementForProductDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Account;
import de.telran.SpringTechnologyBankApp.entities.bank.Agreement;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);


    Account accountDtoToAccount(AccountDto accountDto);


    AccountDto accountToAccountDto(Account account);


    AccountForClientDto accountToAccountForClientDto(Account account);
}
