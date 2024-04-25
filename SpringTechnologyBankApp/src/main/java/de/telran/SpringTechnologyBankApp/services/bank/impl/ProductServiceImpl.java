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

    /**
     * Создает новый продукт на основе предоставленных данных и сохраняет его в базе данных.
     *
     * @param productDto данные о продукте для создания
     * @return созданный продукт в виде {@link ProductDto}
     * @throws DataIntegrityViolationException если произошла ошибка нарушения целостности данных
     * @throws RuntimeException               если возникла непредвиденная ошибка при создании продукта
     */
    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        try {
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

    /**
     * Возвращает информацию о продукте по его идентификатору.
     *
     * @param id идентификатор продукта
     * @return информация о продукте в виде {@link ProductDto}
     * @throws NotFoundEntityException если продукт с указанным идентификатором не найден
     */
    @Override
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::productToProductDto)
                .orElseThrow(() -> new NotFoundEntityException("Не найден продукт с id: " + id));
    }

    /**
     * Обновляет информацию о продукте по его идентификатору.
     *
     * @param id идентификатор продукта
     * @param product информация о продукте для обновления в виде {@link ProductDto}
     * @return обновленная информация о продукте в виде {@link ProductDto}
     * @throws NotFoundEntityException если продукт с указанным идентификатором не найден
     * @throws NotUpdatedEntityException если не удалось обновить информацию о продукте
     */
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

    /**
     * Удаляет продукт по его идентификатору.
     *
     * @param id идентификатор продукта
     * @throws NotFoundEntityException если продукт с указанным идентификатором не найден
     */
    @Override
    public void deleteProductById(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Продукт с id: " + id + " не найден"));
        existingProduct.setStatusType(StatusType.REMOVED);
        productRepository.save(existingProduct);
    }

    /**
     * Получает список всех продуктов с указанным статусом.
     *
     * @param status статус продуктов
     * @return список DTO продуктов с указанным статусом
     * @throws NotFoundEntityException если не найдено ни одного продукта с указанным статусом
     */
    @Override
    public List<ProductDto> getAllProductsWhereStatusTypeIs(StatusType status) {
        List<Product> products = productRepository.findAllByStatusType(status);
        return products.stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());
    }

    /**
     * Получает список всех продуктов определенного типа и валюты.
     *
     * @param type тип продукта
     * @param code код валюты
     * @return список DTO продуктов заданного типа и валюты
     */
    @Override
    public List<ProductDto> getAllProductsByProductTypeAndCurrencyCode(ProductType type, CurrencyCode code) {
        List<Product> products = productRepository.findAllByProductTypeAndCurrencyCode(type, code);
        return products.stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());
    }

    /**
     * Обновляет поля существующего продукта на основе информации из DTO продукта.
     *
     * @param productDto       DTO продукта с обновленной информацией
     * @param existingProduct  существующий продукт, который нужно обновить
     */
    private void updateProductFields(ProductDto productDto, Product existingProduct) {
        updateFieldIfNotNull(productDto.getInterestRate(), existingProduct::setInterestRate);
        updateFieldIfNotNull(productDto.getCurrencyCode(), existingProduct::setCurrencyCode);
        updateFieldIfNotNull(productDto.getProductType(), existingProduct::setProductType);
        updateFieldIfNotNull(productDto.getStatusType(), existingProduct::setStatusType);
        updateFieldIfNotNull(productDto.getLimitSum(), existingProduct::setLimitSum);
        updateFieldIfNotNull(productDto.getName(), existingProduct::setName);
    }
}
