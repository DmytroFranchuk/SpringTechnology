package de.telran.SpringTechnologyBankApp.services.bank.interf;

import de.telran.SpringTechnologyBankApp.dtos.bank.ManagerDto;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ManagerService {
    ManagerDto createManager(ManagerDto manager);

    Optional<ManagerDto> getManagerById(Long id);

    ManagerDto updateManagerById(Long id, ManagerDto manager);

    void deleteManagerById(Long id);

    List<ManagerDto> getAllManagersWhereStatusTypeIs(StatusType status);

    List<ManagerDto> findAllManagersCreatedAfterDate(LocalDateTime createdAt);
}
