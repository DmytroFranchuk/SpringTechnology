package de.telran.SpringTechnologyBankApp.services.bank.interf;

import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ManagerDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ManagerDtoForByCondition;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;

import java.time.LocalDateTime;
import java.util.List;

public interface ManagerService {
    ManagerDto createManager(ManagerDto manager);

    ManagerDto getManagerById(Long id);

    ManagerDto updateManagerById(Long id, ManagerDto manager);

    void deleteManagerById(Long id);

    List<ManagerDtoForByCondition> getAllManagersWhereStatusTypeIs(StatusType status);

    List<ManagerDtoForByCondition> getAllManagersCreatedAfterDate(LocalDateTime createdAt);
}
