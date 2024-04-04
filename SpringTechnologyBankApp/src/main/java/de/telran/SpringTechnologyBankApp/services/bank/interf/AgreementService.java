package de.telran.SpringTechnologyBankApp.services.bank.interf;

import de.telran.SpringTechnologyBankApp.dtos.bank.agreement.AgreementDto;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;

import java.util.List;
import java.util.Optional;

public interface AgreementService {
    AgreementDto createAgreement(AgreementDto agreement);

    Optional<AgreementDto> getAgreementById(Long id);

    AgreementDto updateAgreementById(Long id, AgreementDto agreement);

    void deleteAgreementById(Long id);

    List<AgreementDto> getAllAgreementsWhereStatusTypeIs(StatusType status);

    List<AgreementDto> findAgreementsWhereClientIdIs(Long clientId);
}
