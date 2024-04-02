package de.telran.SpringTechnologyBankApp.services.bank.interf;

import de.telran.SpringTechnologyBankApp.dtos.bank.ProductDto;
import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDto createProduct(ProductDto product);

    Optional<ProductDto> getProductById(Long id);

    ProductDto updateProductById(Long id, ProductDto product);

    void deleteProductById(Long id);

    List<ProductDto> getAllProductsWhereStatusTypeIs(StatusType status);

    List<ProductDto> getAllProductsByProductTypeAndCurrencyCode(ProductType type, CurrencyCode code);

}