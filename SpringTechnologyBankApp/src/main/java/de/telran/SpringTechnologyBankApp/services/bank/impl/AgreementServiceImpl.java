package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.dtos.bank.AgreementDto;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.services.bank.interf.AgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgreementServiceImpl implements AgreementService {

    @Override
    public AgreementDto createAgreement(AgreementDto agreement) {
        return null;
    }

    @Override
    public Optional<AgreementDto> getAgreementById(Long id) {
        return Optional.empty();
    }

    @Override
    public AgreementDto updateAgreementById(Long id, AgreementDto agreement) {
        return null;
    }

    @Override
    public void deleteAgreementById(Long id) {

    }

    @Override
    public List<AgreementDto> getAllAgreementsWhereStatusTypeIs(StatusType status) {
        return null;
    }

    @Override
    public List<AgreementDto> findAgreementsWhereClientIdIs(Long clientId) {
        return null;
    }
}
