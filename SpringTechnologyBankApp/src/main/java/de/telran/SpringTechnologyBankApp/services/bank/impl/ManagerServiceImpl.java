package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.dtos.bank.ManagerDto;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.services.bank.interf.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    @Override
    public ManagerDto createManager(ManagerDto manager) {
        return null;
    }

    @Override
    public Optional<ManagerDto> getManagerById(Long id) {
        return Optional.empty();
    }

    @Override
    public ManagerDto updateManagerById(Long id, ManagerDto manager) {
        return null;
    }

    @Override
    public void deleteManagerById(Long id) {

    }

    @Override
    public List<ManagerDto> getAllManagersWhereStatusTypeIs(StatusType status) {
        return null;
    }

    @Override
    public List<ManagerDto> findAllManagersCreatedAfterDate(LocalDateTime createdAt) {
        return null;
    }
}
