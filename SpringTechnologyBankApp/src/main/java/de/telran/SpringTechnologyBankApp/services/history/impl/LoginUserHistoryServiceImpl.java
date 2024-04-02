package de.telran.SpringTechnologyBankApp.services.history.impl;

import de.telran.SpringTechnologyBankApp.dtos.history.LoginUserHistoryDto;
import de.telran.SpringTechnologyBankApp.services.history.interf.LoginUserHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginUserHistoryServiceImpl implements LoginUserHistoryService {

    @Override
    public void createRecordSuccessfulLogin(LoginUserHistoryDto loginUserRecord) {

    }

    @Override
    public List<LoginUserHistoryDto> getAllRecordsByIpAddress(String ipAddress) {
        return null;
    }

    @Override
    public List<LoginUserHistoryDto> getAllRecordsByClientIdAndDateRange(Long clientId, LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }
}
