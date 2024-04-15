package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.TestData;
import de.telran.SpringTechnologyBankApp.dtos.bank.agreement.AgreementDto;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.mappers.bank.AgreementMapper;
import de.telran.SpringTechnologyBankApp.repositories.bank.AgreementRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgreementServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private AgreementRepository agreementRepository;
    @Mock
    private AgreementMapper agreementMapper;

    @InjectMocks
    private AgreementServiceImpl agreementService;

    @BeforeEach
    void setUp() {
        Mockito.reset(productRepository);
        Mockito.reset(agreementRepository);
    }

    @Test
    void createAgreement() {
        when(agreementMapper.agreementDtoToAgreement(TestData.AGREEMENT_DTO)).thenReturn(TestData.AGREEMENT);
        when(agreementRepository.save(TestData.AGREEMENT)).thenReturn(TestData.AGREEMENT);
        when(agreementMapper.agreementToAgreementDto(TestData.AGREEMENT)).thenReturn(TestData.AGREEMENT_DTO);

        AgreementDto result = agreementService.createAgreement(TestData.AGREEMENT_DTO);
        assertNotNull(result);
        assertEquals(TestData.AGREEMENT_DTO, result);
    }

    @Test
    void createAgreement_NegativeCase_ExceptionThrown() {
        when(agreementMapper.agreementDtoToAgreement(TestData.AGREEMENT_DTO)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> agreementService.createAgreement(TestData.AGREEMENT_DTO));
    }

    @Test
    void getAgreementById() {
        when(agreementRepository.findById(1L)).thenReturn(Optional.of(TestData.AGREEMENT));
        when(agreementMapper.agreementToAgreementDto(TestData.AGREEMENT)).thenReturn(TestData.AGREEMENT_DTO);
        AgreementDto result = agreementService.getAgreementById(1L);
        assertNotNull(result);
        assertEquals(TestData.AGREEMENT.getSum(), result.getSum());
    }

    @Test
    void getAgreementById_NegativeTest() {
        when(agreementRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> agreementService.getAgreementById(1L));
    }

    @Test
    void updateAgreementById() {
        when(agreementRepository.findById(1L)).thenReturn(Optional.of(TestData.AGREEMENT));
        when(productRepository.getReferenceById(1L)).thenReturn(TestData.PRODUCT);
        when(agreementRepository.save(TestData.AGREEMENT)).thenReturn(TestData.AGREEMENT);
        when(agreementMapper.agreementToAgreementDto(TestData.AGREEMENT)).thenReturn(TestData.AGREEMENT_DTO);

        AgreementDto result = agreementService.updateAgreementById(1L, TestData.AGREEMENT_DTO);
        assertNotNull(result);
        assertEquals(TestData.AGREEMENT.getSum(), result.getSum());
    }

    @Test
    void updateAgreementById_NegativeTest() {
        when(agreementRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> agreementService.updateAgreementById(1L, new AgreementDto()));
    }

    @Test
    void deleteAgreementById() {
        when(agreementRepository.findById(1L)).thenReturn(Optional.of(TestData.AGREEMENT));
        when(agreementRepository.save(TestData.AGREEMENT)).thenReturn(TestData.AGREEMENT);
        assertDoesNotThrow(() -> agreementService.deleteAgreementById(1L));
    }

    @Test
    void deleteAgreementById_NegativeTest() {
        when(agreementRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> agreementService.deleteAgreementById(1L));
    }

    @Test
    void getAllAgreementsWhereStatusTypeIs() {
        when(agreementRepository.findAgreementsByStatusType(any())).thenReturn(List.of(TestData.AGREEMENT));
        when(agreementMapper.agreementToAgreementDtoWithoutAccounts(any())).thenReturn(new AgreementDto());
        List<AgreementDto> result = agreementService.getAllAgreementsWhereStatusTypeIs(StatusType.ACTIVE);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAllAgreementsWhereStatusTypeIs_NegativeTest() {
        when(agreementRepository.findAgreementsByStatusType(any())).thenReturn(new ArrayList<>());
        List<AgreementDto> result = agreementService.getAllAgreementsWhereStatusTypeIs(StatusType.ACTIVE);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findAgreementsByProductType() {
        when(agreementRepository.findAgreementsByProductType(any())).thenReturn(List.of(TestData.AGREEMENT));
        when(agreementMapper.agreementToAgreementDtoWithoutAccounts(any())).thenReturn(new AgreementDto());
        List<AgreementDto> result = agreementService.findAgreementsByProductType(ProductType.DEBIT_ACCOUNT);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findAgreementsByProductType_NegativeTest() {
        when(agreementRepository.findAgreementsByProductType(any())).thenReturn(new ArrayList<>());
        List<AgreementDto> result = agreementService.findAgreementsByProductType(ProductType.DEBIT_ACCOUNT);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}