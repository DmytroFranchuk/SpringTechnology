package de.telran.SpringTechnologyBankApp.mappers.bank;

import de.telran.SpringTechnologyBankApp.dtos.bank.product.ProductDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Manager;
import de.telran.SpringTechnologyBankApp.entities.bank.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = AgreementMapper.class)
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "agreements", ignore = true)
    @Mapping(target = "manager.id", source = "managerId")
    Product productDtoToProduct(ProductDto dto);



    @Mapping(target = "managerId", source = "manager.id")
    @Mapping(target = "agreements", source = "agreements")
    ProductDto productToProductDto(Product product);
}