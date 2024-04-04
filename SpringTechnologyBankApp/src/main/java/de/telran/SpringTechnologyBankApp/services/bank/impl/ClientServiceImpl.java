package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.dtos.bank.client.ClientDto;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.services.bank.interf.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    @Override
    public ClientDto createClient(ClientDto client) {
        return null;
    }

    @Override
    public Optional<ClientDto> getClientById(Long id) {
        return Optional.empty();
    }

    @Override
    public ClientDto updateClientById(Long id, ClientDto client) {
        return null;
    }

    @Override
    public void deleteClientById(Long id) {

    }

    @Override
    public List<ClientDto> getAllClientsWhereStatusTypeIs(StatusType status) {
        return null;
    }

    @Override
    public List<ClientDto> getAllClientsWhereBalanceMoreThan(BigDecimal balance) {
        return null;
    }
}
