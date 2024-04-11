package de.telran.SpringTechnologyBankApp.services.bank.interf;

import de.telran.SpringTechnologyBankApp.dtos.bank.agreement.AgreementDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Agreement;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgreementService {
    AgreementDto createAgreement(AgreementDto agreement);

    AgreementDto getAgreementById(Long id);

    AgreementDto updateAgreementById(Long id, AgreementDto agreement);

    void deleteAgreementById(Long id);

    List<AgreementDto> getAllAgreementsWhereStatusTypeIs(StatusType status);

    List<AgreementDto> findAgreementsByProductType(ProductType productType);
}
