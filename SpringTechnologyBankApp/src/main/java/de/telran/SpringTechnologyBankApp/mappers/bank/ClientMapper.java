package de.telran.SpringTechnologyBankApp.mappers.bank;

import de.telran.SpringTechnologyBankApp.dtos.bank.account.AccountDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.client.ClientDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Account;
import de.telran.SpringTechnologyBankApp.entities.bank.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = AccountMapper.class)
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "manager.id", source = "managerId")
    Client clientDtoToClient(ClientDto clientDto);


    @Mapping(target = "login", expression = "java(\"*******\")")
    @Mapping(target = "password", expression = "java(\"*******\")")
    @Mapping(target = "roleType", ignore = true)
    @Mapping(target = "managerId", source = "manager.id")
    @Mapping(target = "accounts", source = "accounts")
    ClientDto clientToClientDto(Client client);

}
