package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.dtos.bank.product.ProductDto;
import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.services.bank.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Override
    public ProductDto createProduct(ProductDto product) {
        return null;
    }

    @Override
    public Optional<ProductDto> getProductById(Long id) {
        return Optional.empty();
    }

    @Override
    public ProductDto updateProductById(Long id, ProductDto product) {
        return null;
    }

    @Override
    public void deleteProductById(Long id) {

    }

    @Override
    public List<ProductDto> getAllProductsWhereStatusTypeIs(StatusType status) {
        return null;
    }

    @Override
    public List<ProductDto> getAllProductsByProductTypeAndCurrencyCode(ProductType type, CurrencyCode code) {
        return null;
    }
}
