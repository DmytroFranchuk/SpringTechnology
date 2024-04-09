package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.dtos.bank.client.ClientDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Client;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotUpdatedEntityException;
import de.telran.SpringTechnologyBankApp.mappers.bank.ClientMapper;
import de.telran.SpringTechnologyBankApp.repositories.bank.ClientRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.ManagerRepository;
import de.telran.SpringTechnologyBankApp.services.bank.interf.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static de.telran.SpringTechnologyBankApp.services.utilities.Utils.updateFieldIfNotNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ManagerRepository managerRepository;
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientDto createClient(ClientDto clientDto) {
        try {
            Client client = clientMapper.clientDtoToClient(clientDto);
            Client savedClient = clientRepository.save(client);
            return clientMapper.clientToClientDto(savedClient);
        } catch (DataIntegrityViolationException exception) {
            String errorMessage = exception.getMessage();
            throw new DataIntegrityViolationException(errorMessage);
        } catch (Exception exception) {
            log.error("Не удалось создать клиента", exception);
            throw new RuntimeException("Не удалось создать клиента", exception);
        }
    }

    @Override
    public ClientDto getClientById(Long id) {
        return clientRepository.findById(id).map(clientMapper::clientToClientDto)
                .orElseThrow(() -> new NotFoundEntityException("Не найден клиент с id: " + id));
    }

    @Override
    public ClientDto updateClientById(Long id, ClientDto client) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Клиент с id: " + id + " не найден"));
        updateClientFields(client, existingClient);
        existingClient.setManager(managerRepository.getReferenceById(client.getManagerId()));
        try {
            Client updatedClient = clientRepository.save(existingClient);
            return clientMapper.clientToClientDto(updatedClient);
        } catch (Exception exception) {
            throw new NotUpdatedEntityException("Не удалось обновить клиента с id: " + id);
        }
    }

    @Override
    public void deleteClientById(Long id) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Клиент с id: " + id + " не найден"));
        existingClient.setStatusType(StatusType.REMOVED);
        clientRepository.save(existingClient);
    }

    @Override
    public List<ClientDto> getAllClientsWhereStatusTypeIs(StatusType status) {
        List<Client> clientsWithStatus = clientRepository.findClientsByStatusType(status);
        return clientsWithStatus.stream()
                .map(clientMapper::clientToClientDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientDto> getAllClientsWhereBalanceMoreThan(BigDecimal balance) {
        List<Client> clientsWithBalanceMoreThan = clientRepository.findAllClientsWithBalanceMoreThan(balance);
        return clientsWithBalanceMoreThan.stream()
                .map(clientMapper::clientToClientDto)
                .collect(Collectors.toList());
    }

    private void updateClientFields(ClientDto clientDto, Client existingClient) {
        updateFieldIfNotNull(clientDto.getStatusType(), existingClient::setStatusType);
        updateFieldIfNotNull(clientDto.getTaxNumber(), existingClient::setTaxNumber);
        updateFieldIfNotNull(clientDto.getFirstName(), existingClient::setFirstName);
        updateFieldIfNotNull(clientDto.getLastName(), existingClient::setLastName);
        updateFieldIfNotNull(clientDto.getRoleType(), existingClient::setRoleType);
        updateFieldIfNotNull(clientDto.getPassword(), existingClient::setPassword);
        updateFieldIfNotNull(clientDto.getAddress(), existingClient::setAddress);
        updateFieldIfNotNull(clientDto.getPhone(), existingClient::setPhone);
        updateFieldIfNotNull(clientDto.getLogin(), existingClient::setLogin);
        updateFieldIfNotNull(clientDto.getEmail(), existingClient::setEmail);
    }
}
