package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.TestData;
import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ManagerDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ManagerDtoForByCondition;
import de.telran.SpringTechnologyBankApp.entities.bank.Manager;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.exceptions.NotCreationEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotUpdatedEntityException;
import de.telran.SpringTechnologyBankApp.mappers.bank.ManagerMapper;
import de.telran.SpringTechnologyBankApp.repositories.bank.ClientRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.ManagerRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManagerServiceImplTest {
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ManagerMapper managerMapper;

    @InjectMocks
    private ManagerServiceImpl managerService;

    @BeforeEach
    void setUp() {
        Mockito.reset(managerRepository);
        Mockito.reset(clientRepository);
        Mockito.reset(productRepository);
    }

    @Test
    void createManager() {
        when(managerMapper.managerDtoToManager(TestData.MANAGER_DTO)).thenReturn(TestData.MANAGER);
        when(managerRepository.save(TestData.MANAGER)).thenReturn(TestData.MANAGER);
        when(managerMapper.managerToManagerDto(TestData.MANAGER)).thenReturn(TestData.MANAGER_DTO);
        ManagerDto result = managerService.createManager(TestData.MANAGER_DTO);
        assertNotNull(result);
        assertEquals(TestData.MANAGER_DTO, result);
    }

    @Test
    void createManager_Failure() {
        ManagerDto managerDto = new ManagerDto();
        when(managerMapper.managerDtoToManager(managerDto)).thenReturn(new Manager());
        when(managerRepository.save(any())).thenReturn(null);

        assertThrows(NotCreationEntityException.class, () -> managerService.createManager(managerDto));
    }

    @Test
    void getManagerById() {
        Long id = 1L;
        when(managerRepository.findById(id)).thenReturn(Optional.of(TestData.MANAGER));
        when(managerMapper.managerToManagerDto(TestData.MANAGER)).thenReturn(TestData.MANAGER_DTO);
        ManagerDto result = managerService.getManagerById(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void getManagerById_Negative() {
        Long id = 999L;
        when(managerRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> managerService.getManagerById(id));
    }

    @Test
    void updateManagerById() {
        Long id = 1L;
        when(managerRepository.findById(id)).thenReturn(Optional.of(TestData.MANAGER_NEW));
        when(managerRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(managerMapper.managerToManagerDto(TestData.MANAGER_NEW)).thenReturn(TestData.MANAGER_DTO_NEW);
        ManagerDto result = managerService.updateManagerById(id, TestData.MANAGER_DTO_NEW);
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(TestData.MANAGER_NEW.getFirstName(), result.getFirstName());
    }

    @Test
    void updateManagerById_Negative() {
        Long id = 999L;
        when(managerRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> managerService.updateManagerById(id, TestData.MANAGER_DTO));
    }

    @Test
    void updateManagerById_Success() {
        Long id = 1L;
        ManagerDto managerDto = TestData.MANAGER_DTO_NEW;
        Manager existingManager = TestData.MANAGER_NEW;
        when(managerRepository.findById(id)).thenReturn(Optional.of(existingManager));
        when(managerRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(managerMapper.managerToManagerDto(existingManager)).thenReturn(managerDto);
        ManagerDto result = managerService.updateManagerById(id, managerDto);
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(managerDto.getFirstName(), result.getFirstName());
    }

    @Test
    void updateManagerById_NotFound() {
        Long id = 999L;
        ManagerDto managerDto = TestData.MANAGER_DTO_NEW;
        when(managerRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> managerService.updateManagerById(id, managerDto));
    }

    @Test
    void deleteManagerById() {
        Long id = 1L;
        when(managerRepository.findById(id)).thenReturn(Optional.of(TestData.MANAGER));
        when(clientRepository.findAllByManagerId(id)).thenReturn(Collections.emptyList());
        when(productRepository.findAllByManagerId(id)).thenReturn(Collections.emptyList());

        managerService.deleteManagerById(id);
        verify(managerRepository).save(TestData.MANAGER);
        verify(clientRepository).saveAll(Collections.emptyList());
        verify(productRepository).saveAll(Collections.emptyList());
    }

    @Test
    void deleteManagerById_Negative() {
        Long id = 999L;
        when(managerRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> managerService.deleteManagerById(id));
        verify(managerRepository, never()).save(any());
    }

    @Test
    void getAllManagersWhereStatusTypeIs() {
        StatusType statusType = StatusType.ACTIVE;
        List<Manager> managers = List.of(TestData.MANAGER);
        when(managerRepository.findAllByStatusType(statusType)).thenReturn(managers);
        List<ManagerDtoForByCondition> result = managerService.getAllManagersWhereStatusTypeIs(statusType);

        assertEquals(managers.size(), result.size());
        assertEquals(managers.stream().map(managerMapper::managerToManagerDtoWithoutCollections).collect(Collectors.toList()), result);
    }

    @Test
    void getAllManagersWhereStatusTypeIs_Negative() {
        StatusType statusType = StatusType.ACTIVE;
        when(managerRepository.findAllByStatusType(statusType)).thenReturn(new ArrayList<>());
        assertThrows(NotFoundEntityException.class, () -> managerService.getAllManagersWhereStatusTypeIs(statusType));
    }

    @Test
    void getAllManagersCreatedAfterDate() {
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        List<Manager> managers = List.of(TestData.MANAGER);
        when(managerRepository.findManagersByCreatedAtAfter(createdAt)).thenReturn(managers);
        List<ManagerDtoForByCondition> result = managerService.getAllManagersCreatedAfterDate(createdAt);
        assertEquals(managers.size(), result.size());
        assertEquals(managers.stream().map(managerMapper::managerToManagerDtoWithoutCollections).collect(Collectors.toList()), result);
    }

    @Test
    void getAllManagersCreatedAfterDate_Negative() {
        LocalDateTime createdAt = LocalDateTime.now().plusDays(1);
        when(managerRepository.findManagersByCreatedAtAfter(createdAt)).thenReturn(new ArrayList<>());
        List<ManagerDtoForByCondition> result = managerService.getAllManagersCreatedAfterDate(createdAt);
        assertTrue(result.isEmpty());
    }

    @Test
    void updateManagerById_ExceptionThrown() {
        Long id = 1L;
        ManagerDto managerDto = TestData.MANAGER_DTO_NEW;
        when(managerRepository.findById(id)).thenReturn(Optional.of(TestData.MANAGER_NEW));
        when(managerRepository.save(any())).thenThrow(RuntimeException.class);
        assertThrows(NotUpdatedEntityException.class, () -> managerService.updateManagerById(id, managerDto));
    }
}