package de.telran.SpringTechnologyBankApp.mappers.bank;

import de.telran.SpringTechnologyBankApp.dtos.bank.agreement.AgreementDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.product.AgreementForProductDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Agreement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AgreementMapper {
    AgreementMapper INSTANCE = Mappers.getMapper(AgreementMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "product.id", source = "productId")
    Agreement agreementDtoToAgreement(AgreementDto agreementDto);


    @Mapping(target = "accounts", source = "accounts")
    @Mapping(target = "productId", source = "product.id")
    AgreementDto agreementToAgreementDto(Agreement agreement);

    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "productId", source = "product.id")
    AgreementDto agreementToAgreementDtoWithoutAccounts(Agreement agreement);

    AgreementForProductDto agreementToAgreementForProductDto(Agreement agreement);
}
