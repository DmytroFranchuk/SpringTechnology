package de.telran.SpringTechnologyBankApp.mappers.bank;

import de.telran.SpringTechnologyBankApp.dtos.bank.account.AccountDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.client.ClientDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Account;
import de.telran.SpringTechnologyBankApp.entities.bank.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);


    Client clientDtoToClient(ClientDto clientDto);


    ClientDto clientToClientDto(Client client);

}
