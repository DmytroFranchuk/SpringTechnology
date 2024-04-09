package de.telran.SpringTechnologyBankApp.services.bank.interf;

import de.telran.SpringTechnologyBankApp.dtos.bank.client.ClientDto;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ClientService {
    ClientDto createClient(ClientDto client);

    ClientDto getClientById(Long id);

    ClientDto updateClientById(Long id, ClientDto client);

    void deleteClientById(Long id);

    List<ClientDto> getAllClientsWhereStatusTypeIs(StatusType status);

    List<ClientDto> getAllClientsWhereBalanceMoreThan(BigDecimal balance);
}
