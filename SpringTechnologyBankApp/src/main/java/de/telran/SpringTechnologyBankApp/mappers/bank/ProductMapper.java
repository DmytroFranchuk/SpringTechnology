package de.telran.SpringTechnologyBankApp.mappers.bank;

import de.telran.SpringTechnologyBankApp.dtos.bank.product.ProductDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "agreements", ignore = true)
    Product productDtoToProduct(ProductDto dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "statusType", source = "statusType")
    @Mapping(target = "productType", source = "productType")
    @Mapping(target = "currencyCode", source = "currencyCode")
    @Mapping(target = "interestRate", source = "interestRate")
    @Mapping(target = "limitSum", source = "limitSum")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    ProductDto productToProductDto(Product product);
}
