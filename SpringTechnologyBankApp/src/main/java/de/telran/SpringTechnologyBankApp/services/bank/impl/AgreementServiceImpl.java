package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.dtos.bank.agreement.AgreementDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Agreement;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.exceptions.NotActiveEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotExistEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotUpdatedEntityException;
import de.telran.SpringTechnologyBankApp.mappers.bank.AgreementMapper;
import de.telran.SpringTechnologyBankApp.repositories.bank.AgreementRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.ClientRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.ProductRepository;
import de.telran.SpringTechnologyBankApp.services.bank.interf.AgreementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static de.telran.SpringTechnologyBankApp.services.utilities.Utils.updateFieldIfNotNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgreementServiceImpl implements AgreementService {
    private final AgreementRepository agreementRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final AgreementMapper agreementMapper;

    @Override
    public AgreementDto createAgreement(AgreementDto agreementDto) {
        try {
            Agreement agreement = agreementMapper.agreementDtoToAgreement(agreementDto);
            Agreement savedAgreement = agreementRepository.save(agreement);
            return agreementMapper.agreementToAgreementDto(savedAgreement);
        } catch (DataIntegrityViolationException exception) {
            String errorMessage = exception.getMessage();
            throw new DataIntegrityViolationException(errorMessage);
        } catch (Exception exception) {
            log.error("Не удалось создать ДОГОВОР", exception);
            throw new RuntimeException("Не удалось создать ДОГОВОР", exception);
        }
    }

    @Override
    public AgreementDto getAgreementById(Long id) {
        return agreementRepository.findById(id).map(agreementMapper::agreementToAgreementDto)
                .orElseThrow(() -> new NotFoundEntityException("Не найден ДОГОВОР с id: " + id));
    }

    @Override
    public AgreementDto updateAgreementById(Long id, AgreementDto agreementDto) {
        Agreement existingAgreement = agreementRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Договор с id: " + id + " не найден"));
        updateAgreementFields(agreementDto, existingAgreement);

        existingAgreement.setProduct(productRepository.getReferenceById(agreementDto.getProductId()));
        try {
            Agreement updatedAgreement = agreementRepository.save(existingAgreement);
            return agreementMapper.agreementToAgreementDto(updatedAgreement);
        } catch (Exception exception) {
            throw new NotUpdatedEntityException("Не удалось обновить договор с id: " + id);
        }
    }

    @Override
    public void deleteAgreementById(Long id) {
        Agreement existingAgreement = agreementRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Договор с id: " + id + " не найден"));
        existingAgreement.setStatusType(StatusType.REMOVED);
        agreementRepository.save(existingAgreement);
    }

    @Override
    public List<AgreementDto> getAllAgreementsWhereStatusTypeIs(StatusType status) {
        List<Agreement> agreementsWithStatus = agreementRepository.findAgreementsByStatusType(status);
        return agreementsWithStatus.stream()
                .map(agreementMapper::agreementToAgreementDtoWithoutAccounts)
                .collect(Collectors.toList());
    }

    @Override
    public List<AgreementDto> findAgreementsByProductType(ProductType productType) {
        List<Agreement> agreements = agreementRepository.findAgreementsByProductType(productType);
        return agreements.stream()
                .map(agreementMapper::agreementToAgreementDtoWithoutAccounts)
                .collect(Collectors.toList());
    }

    private void updateAgreementFields(AgreementDto agreementDto, Agreement existingAgreement) {
        updateFieldIfNotNull(agreementDto.getInterestRate(), existingAgreement::setInterestRate);
        updateFieldIfNotNull(agreementDto.getCurrencyCode(), existingAgreement::setCurrencyCode);
        updateFieldIfNotNull(agreementDto.getStatusType(), existingAgreement::setStatusType);
        updateFieldIfNotNull(agreementDto.getSum(), existingAgreement::setSum);
    }
}
