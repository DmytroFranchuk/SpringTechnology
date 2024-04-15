package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.TestData;
import de.telran.SpringTechnologyBankApp.dtos.bank.client.ClientDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Client;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotUpdatedEntityException;
import de.telran.SpringTechnologyBankApp.mappers.bank.ClientMapper;
import de.telran.SpringTechnologyBankApp.repositories.bank.ClientRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.ManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        Mockito.reset(clientRepository);
        Mockito.reset(managerRepository);
    }

    @Test
    void createClient() {
        when(clientMapper.clientDtoToClient(any(ClientDto.class))).thenReturn(TestData.CLIENT);
        when(clientRepository.save(any(Client.class))).thenReturn(TestData.CLIENT);
        when(clientMapper.clientToClientDto(any())).thenReturn(TestData.CLIENT_DTO);
        ClientDto result = clientService.createClient(TestData.CLIENT_DTO);
        assertNotNull(result);
        assertEquals(TestData.CLIENT.getFirstName(), result.getFirstName());
        assertEquals(TestData.CLIENT.getLastName(), result.getLastName());
    }

    @Test
    void createClient_RuntimeException() {
        when(clientRepository.save(any(Client.class))).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> clientService.createClient(TestData.CLIENT_DTO));
    }

    @Test
    void createClient_DataIntegrityViolationException() {
        when(clientMapper.clientDtoToClient(any(ClientDto.class))).thenReturn(TestData.CLIENT);
        when(clientRepository.save(any(Client.class))).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DataIntegrityViolationException.class, () -> clientService.createClient(TestData.CLIENT_DTO));
    }

    @Test
    void getClientById() {
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(TestData.CLIENT));
        when(clientMapper.clientToClientDto(TestData.CLIENT)).thenReturn(TestData.CLIENT_DTO);
        ClientDto result = clientService.getClientById(clientId);

        assertNotNull(result);
        assertEquals(TestData.CLIENT_DTO, result);
    }

    @Test
    void getClientById_Negative() {
        Long clientId = 999L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> clientService.getClientById(clientId));
    }

    @Test
    void updateClientById() {
        Long clientId = 1L;
        Long managerId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(TestData.CLIENT));
        when(managerRepository.getReferenceById(managerId)).thenReturn(TestData.MANAGER);
        when(clientRepository.save(TestData.CLIENT)).thenReturn(TestData.CLIENT);
        when(clientMapper.clientToClientDto(TestData.CLIENT)).thenReturn(TestData.CLIENT_DTO);

        ClientDto result = clientService.updateClientById(clientId, TestData.CLIENT_DTO);
        assertNotNull(result);
        assertEquals(TestData.CLIENT_DTO, result);
    }

    @Test
    void updateClientById_ThrowNotFoundEntityException() {
        Long clientId = 999L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> clientService.updateClientById(clientId, TestData.CLIENT_DTO));
    }

    @Test
    void updateClientById_ThrowNotUpdatedEntityException() {
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(new Client()));
        when(clientRepository.save(any())).thenThrow(new RuntimeException());
        assertThrows(NotUpdatedEntityException.class, () -> clientService.updateClientById(clientId, TestData.CLIENT_DTO));
    }

    @Test
    void deleteClientById() {
        Client client = TestData.CLIENT;
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        clientService.deleteClientById(1L);
        verify(clientRepository).findById(1L);
        assertEquals(StatusType.REMOVED, client.getStatusType());
    }

    @Test
    void deleteClientById_Negative() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> clientService.deleteClientById(1L));
    }

    @Test
    void getAllClientsWhereStatusTypeIs() {
        when(clientRepository.findClientsByStatusType(StatusType.ACTIVE))
                .thenReturn(List.of(TestData.CLIENT));
        when(clientMapper.clientToClientDto(TestData.CLIENT))
                .thenReturn(TestData.CLIENT_DTO);
        List<ClientDto> result = clientService.getAllClientsWhereStatusTypeIs(StatusType.ACTIVE);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TestData.CLIENT_DTO, result.get(0));
    }

    @Test
    void getAllClientsWhereStatusTypeIs_Negative_NotFound() {
        when(clientRepository.findClientsByStatusType(StatusType.ACTIVE))
                .thenReturn(Collections.emptyList());
        List<ClientDto> result = clientService.getAllClientsWhereStatusTypeIs(StatusType.ACTIVE);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllClientsWhereBalanceMoreThan() {
        BigDecimal balanceThreshold = new BigDecimal("1000");
        when(clientRepository.findAllClientsWithBalanceMoreThan(balanceThreshold))
                .thenReturn(List.of(TestData.CLIENT));
        when(clientMapper.clientToClientDto(TestData.CLIENT))
                .thenReturn(TestData.CLIENT_DTO);
        List<ClientDto> result = clientService.getAllClientsWhereBalanceMoreThan(balanceThreshold);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TestData.CLIENT_DTO, result.get(0));
    }

    @Test
    void getAllClientsWhereBalanceMoreThan_Negative_EmptyList() {
        BigDecimal balanceThreshold = new BigDecimal("1000");
        when(clientRepository.findAllClientsWithBalanceMoreThan(balanceThreshold))
                .thenReturn(Collections.emptyList());
        List<ClientDto> result = clientService.getAllClientsWhereBalanceMoreThan(balanceThreshold);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}