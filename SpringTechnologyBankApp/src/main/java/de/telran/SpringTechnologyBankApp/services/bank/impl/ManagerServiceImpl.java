package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ManagerDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ManagerDtoForByCondition;
import de.telran.SpringTechnologyBankApp.entities.bank.Client;
import de.telran.SpringTechnologyBankApp.entities.bank.Manager;
import de.telran.SpringTechnologyBankApp.entities.bank.Product;
import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.exceptions.NotCreationEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotDeletionEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotUpdatedEntityException;
import de.telran.SpringTechnologyBankApp.mappers.bank.ManagerMapper;
import de.telran.SpringTechnologyBankApp.repositories.bank.ClientRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.ManagerRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.ProductRepository;
import de.telran.SpringTechnologyBankApp.services.bank.interf.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.telran.SpringTechnologyBankApp.services.utilities.Utils.updateFieldIfNotNull;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final ManagerMapper managerMapper;


    @Override
    public ManagerDto createManager(ManagerDto managerDto) {
        return Optional.of(managerDto)
                .map(managerMapper::managerDtoToManager)
                .map(managerRepository::save)
                .map(managerMapper::managerToManagerDto)
                .orElseThrow(() -> new NotCreationEntityException("Не удалось создать менеджера"));
    }

    @Override
    public ManagerDto getManagerById(Long id) {
        return managerRepository.findById(id).map(managerMapper::managerToManagerDto)
                .orElseThrow(() -> new NotFoundEntityException("Не найден менеджер с id: " + id));
    }

    @Override
    public ManagerDto updateManagerById(Long id, ManagerDto manager) {
        Manager existingManager = managerRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Менеджер с id: " + id + " не найден"));
        updateFieldIfNotNull(manager.getFirstName(), existingManager::setFirstName);
        updateFieldIfNotNull(manager.getLastName(), existingManager::setLastName);
        updateFieldIfNotNull(manager.getLogin(), existingManager::setLogin);
        updateFieldIfNotNull(manager.getPassword(), existingManager::setPassword);
        updateFieldIfNotNull(manager.getEmail(), existingManager::setEmail);
        updateFieldIfNotNull(manager.getDescription(), existingManager::setDescription);
        updateFieldIfNotNull(manager.getStatusType(), existingManager::setStatusType);
        try {
            Manager updatedManager = managerRepository.save(existingManager);
            return managerMapper.managerToManagerDto(updatedManager);
        } catch (Exception exception) {
            throw new NotUpdatedEntityException("Не удалось обновить менеджера с id: " + id);
        }
    }

    @Override
    @Transactional
    public void deleteManagerById(Long id) {
        Manager managerToDelete = managerRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Менеджер с id: " + id + " не найден"));
        try {
            managerToDelete.setStatusType(StatusType.REMOVED);
            managerRepository.save(managerToDelete);
            Manager transitManager = createTransitManager();
            managerRepository.save(transitManager);
            List<Client> clientsToTransfer = clientRepository.findAllByManagerId(id);
            List<Product> productsToTransfer = productRepository.findAllByManagerId(id);
            clientsToTransfer.forEach(client -> client.setManager(transitManager));
            productsToTransfer.forEach(product -> product.setManager(transitManager));
            clientRepository.saveAll(clientsToTransfer);
            productRepository.saveAll(productsToTransfer);
        } catch (Exception exception) {
            throw new NotDeletionEntityException("Не удалось удалить менеджера с id: " + id);
        }
    }

    @Override
    public List<ManagerDtoForByCondition> getAllManagersWhereStatusTypeIs(StatusType status) {
        List<Manager> managers = managerRepository.findAllByStatusType(status);
        if (managers.isEmpty()) {
            throw new NotFoundEntityException("Не найдено менеджеров с указанным статусом: " + status);
        }
        return managers.stream()
                .map(managerMapper::managerToManagerDtoWithoutCollections)
                .collect(Collectors.toList());
    }

    @Override
    public List<ManagerDtoForByCondition> getAllManagersCreatedAfterDate(LocalDateTime createdAt) {
        List<Manager> managers = managerRepository.findManagersByCreatedAtAfter(createdAt);
        return managers.stream()
                .map(managerMapper::managerToManagerDtoWithoutCollections)
                .collect(Collectors.toList());
    }

    private Manager createTransitManager() {
        Manager transitManager = new Manager();
        transitManager.setStatusType(StatusType.INACTIVE);
        transitManager.setRoleType(RoleType.ROLE_MANAGER);
        transitManager.setFirstName("Transit_Manager");
        transitManager.setLastName("Transit_Manager");
        transitManager.setDescription("Transit_Manager");
        transitManager.setEmail("Transit_Manager");
        transitManager.setLogin("Transit_Manager");
        transitManager.setPassword("Transit_Manager");
        return transitManager;
    }
}