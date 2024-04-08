package de.telran.SpringTechnologyBankApp.mappers.bank;

import de.telran.SpringTechnologyBankApp.dtos.bank.agreement.AgreementDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.product.AgreementForProductDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Agreement;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AgreementMapper {
    AgreementMapper INSTANCE = Mappers.getMapper(AgreementMapper.class);


    Agreement agreementDtoToAgreement(AgreementDto agreementDto);


    AgreementDto agreementToAgreementDto(Agreement agreement);


    AgreementForProductDto agreementToAgreementForProductDto(Agreement agreement);

}
