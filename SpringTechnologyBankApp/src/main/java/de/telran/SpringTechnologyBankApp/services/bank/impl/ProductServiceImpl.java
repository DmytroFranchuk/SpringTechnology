package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.dtos.bank.product.ProductDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Product;
import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotUpdatedEntityException;
import de.telran.SpringTechnologyBankApp.mappers.bank.ProductMapper;
import de.telran.SpringTechnologyBankApp.repositories.bank.AgreementRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.ManagerRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.ProductRepository;
import de.telran.SpringTechnologyBankApp.services.bank.interf.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static de.telran.SpringTechnologyBankApp.services.utilities.Utils.updateFieldIfNotNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    //    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final AgreementRepository agreementRepository;
    private final ManagerRepository managerRepository;
    private final ProductMapper productMapper;


    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        try {
//            return Optional.of(productDto)
//                    .map(productMapper::productDtoToProduct)
//                    .map(productRepository::save)
//                    .map(productMapper::productToProductDto)
//                    .orElseThrow(() -> new NotCreationEntityException("Не удалось создать продукт"));
            Product product = productMapper.productDtoToProduct(productDto);
            Product savedProduct = productRepository.save(product);
            return productMapper.productToProductDto(savedProduct);
        } catch (DataIntegrityViolationException exception) {
            String errorMessage = exception.getMessage();
            throw new DataIntegrityViolationException(errorMessage);
        } catch (Exception exception) {
            log.error("Не удалось создать продукт", exception);
            throw new RuntimeException("Не удалось создать продукт", exception);
        }
    }

    @Override
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::productToProductDto)
                .orElseThrow(() -> new NotFoundEntityException("Не найден продукт с id: " + id));
    }

    @Override
    public ProductDto updateProductById(Long id, ProductDto product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Продукт с id: " + id + " не найден"));
        updateProductFields(product, existingProduct);
        // проверить есть ли в базе с таким номером менеджер
        existingProduct.setManager(managerRepository.getReferenceById(product.getManagerId()));
        try {
            Product updatedProduct = productRepository.save(existingProduct);
            return productMapper.productToProductDto(updatedProduct);
        } catch (Exception exception) {
            throw new NotUpdatedEntityException("Не удалось обновить продукт с id: " + id);
        }
    }

    @Override
    public void deleteProductById(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Продукт с id: " + id + " не найден"));
        existingProduct.setStatusType(StatusType.REMOVED);
        productRepository.save(existingProduct);
    }

    @Override
    public List<ProductDto> getAllProductsWhereStatusTypeIs(StatusType status) {
        List<Product> products = productRepository.findAllByStatusType(status);
        return products.stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProductsByProductTypeAndCurrencyCode(ProductType type, CurrencyCode code) {
        List<Product> products = productRepository.findAllByProductTypeAndCurrencyCode(type, code);
        return products.stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());
    }

    private void updateProductFields(ProductDto productDto, Product existingProduct) {
        updateFieldIfNotNull(productDto.getInterestRate(), existingProduct::setInterestRate);
        updateFieldIfNotNull(productDto.getCurrencyCode(), existingProduct::setCurrencyCode);
        updateFieldIfNotNull(productDto.getProductType(), existingProduct::setProductType);
        updateFieldIfNotNull(productDto.getStatusType(), existingProduct::setStatusType);
        updateFieldIfNotNull(productDto.getLimitSum(), existingProduct::setLimitSum);
        updateFieldIfNotNull(productDto.getName(), existingProduct::setName);
    }
}
