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

    /**
     * Создает нового менеджера на основе переданного объекта {@code managerDto}.
     * Преобразует объект {@code managerDto} в сущность менеджера, сохраняет его в репозитории
     * и возвращает преобразованный обратно в DTO формат результат.
     * Если переданный {@code managerDto} равен {@code null} или не удалось сохранить менеджера в репозитории,
     * выбрасывает исключение {@link NotCreationEntityException}.
     *
     * @param managerDto объект DTO, содержащий информацию о новом менеджере
     * @return DTO объект, содержащий информацию о созданном менеджере
     * @throws NotCreationEntityException если не удалось создать менеджера
     */
    @Override
    public ManagerDto createManager(ManagerDto managerDto) {
        return Optional.of(managerDto)
                .map(managerMapper::managerDtoToManager)
                .map(managerRepository::save)
                .map(managerMapper::managerToManagerDto)
                .orElseThrow(() -> new NotCreationEntityException("Не удалось создать менеджера"));
    }

    /**
     * Возвращает информацию о менеджере по указанному идентификатору {@code id}.
     * Если менеджер с указанным идентификатором не найден в репозитории, выбрасывает исключение {@link NotFoundEntityException}.
     *
     * @param id идентификатор менеджера
     * @return объект DTO, содержащий информацию о менеджере
     * @throws NotFoundEntityException если менеджер с указанным идентификатором не найден
     */
    @Override
    public ManagerDto getManagerById(Long id) {
        return managerRepository.findById(id).map(managerMapper::managerToManagerDto)
                .orElseThrow(() -> new NotFoundEntityException("Не найден менеджер с id: " + id));
    }

    /**
     * Обновляет информацию о менеджере с указанным идентификатором {@code id}.
     * Если менеджер с указанным идентификатором не найден в репозитории, выбрасывает исключение {@link NotFoundEntityException}.
     * Если происходит ошибка при сохранении обновленной информации в репозитории, выбрасывается исключение {@link NotUpdatedEntityException}.
     *
     * @param id       идентификатор менеджера
     * @param manager  объект DTO с новой информацией о менеджере
     * @return объект DTO, содержащий обновленную информацию о менеджере
     * @throws NotFoundEntityException  если менеджер с указанным идентификатором не найден
     * @throws NotUpdatedEntityException  если произошла ошибка при обновлении информации о менеджере
     */
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

    /**
     * Удаляет менеджера с указанным идентификатором {@code id}.
     * Если менеджер с указанным идентификатором не найден в репозитории, выбрасывает исключение {@link NotFoundEntityException}.
     * Если происходит ошибка при сохранении обновленной информации в репозитории, выбрасывается исключение {@link NotDeletionEntityException}.
     * Если статус менеджера, который требуется удалить, уже установлен как {@link StatusType#REMOVED}, возвращает {@code false},
     * в противном случае устанавливает статус менеджера в {@link StatusType#REMOVED}, создает временного менеджера для переноса клиентов и продуктов,
     * переносит клиентов и продукты к временному менеджеру и сохраняет изменения в репозитории.
     *
     * @param id идентификатор менеджера, который требуется удалить
     * @return {@code true}, если удаление успешно выполнено; {@code false}, если статус менеджера уже установлен как {@link StatusType#REMOVED}
     * @throws NotFoundEntityException      если менеджер с указанным идентификатором не найден
     * @throws NotDeletionEntityException  если произошла ошибка при удалении менеджера
     */
    @Override
    @Transactional
    public boolean deleteManagerById(Long id) {
        Manager managerToDelete = managerRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Менеджер с id: " + id + " не найден"));
        try {
            if (managerToDelete.getStatusType() != StatusType.REMOVED) {
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
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            throw new NotDeletionEntityException("Не удалось удалить менеджера с id: " + id);
        }
    }

    /**
     * Возвращает список всех менеджеров с указанным статусом {@code status}.
     * Если менеджеры с указанным статусом не найдены в репозитории, выбрасывается исключение {@link NotFoundEntityException}.
     *
     * @param status статус менеджеров, которых требуется найти
     * @return список менеджеров с указанным статусом
     * @throws NotFoundEntityException если не найдено менеджеров с указанным статусом
     */
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

    /**
     * Возвращает список всех менеджеров, созданных после указанной даты {@code createdAt}.
     * Если менеджеры, созданные после указанной даты, не найдены в репозитории,
     * возвращается пустой список.
     *
     * @param createdAt дата, после которой созданы менеджеры, которых требуется найти
     * @return список менеджеров, созданных после указанной даты
     */
    @Override
    public List<ManagerDtoForByCondition> getAllManagersCreatedAfterDate(LocalDateTime createdAt) {
        List<Manager> managers = managerRepository.findManagersByCreatedAtAfter(createdAt);
        return managers.stream()
                .map(managerMapper::managerToManagerDtoWithoutCollections)
                .collect(Collectors.toList());
    }

    /**
     * Создает и возвращает менеджера-транзита.
     * Менеджер-транзит создается со следующими свойствами:
     * - Статус установлен в {@link StatusType#INACTIVE}.
     * - Роль установлена в {@link RoleType#ROLE_MANAGER}.
     * - Имя, фамилия, описание, email, логин и пароль установлены в "Transit_Manager".
     *
     * @return менеджер-транзит
     */
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