package de.telran.SpringTechnologyBankApp.services.history.interf;

import de.telran.SpringTechnologyBankApp.dtos.history.LoginUserHistoryDto;

import java.time.LocalDateTime;
import java.util.List;

public interface LoginUserHistoryService {
    void createRecordSuccessfulLogin(LoginUserHistoryDto loginUserRecord);

    List<LoginUserHistoryDto> getAllRecordsByIpAddress(String ipAddress);

    List<LoginUserHistoryDto> getAllRecordsByClientIdAndDateRange(
            Long clientId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

}
