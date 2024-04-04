package de.telran.SpringTechnologyBankApp.mappers.bank;

import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ClientForManagerDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ProductForManagerDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring")
public interface ProductForManagerMapper {

    ProductForManagerMapper INSTANCE = Mappers.getMapper(ProductForManagerMapper.class);

    @Named("mapToProducts")
    ProductForManagerDto productToProductForManagerDto(Product product);

//    @Named("sortProductsById")
//    static Comparator<ProductForManagerDto> sortProductsById() {
//        return Comparator.comparing(ProductForManagerDto::getId);
//    }



}

//    default List<ProductForManagerDto> mapProducts(Set<Product> products) {
//        return products.stream()
//                .map(this::productToProductForManagerDto)
//                .sorted(Comparator.comparing(ProductForManagerDto::getId))
//                .collect(Collectors.toList());
//    }
