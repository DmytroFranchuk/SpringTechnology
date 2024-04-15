package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.TestData;
import de.telran.SpringTechnologyBankApp.dtos.bank.product.ProductDto;
import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.mappers.bank.ProductMapper;
import de.telran.SpringTechnologyBankApp.repositories.bank.ManagerRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        Mockito.reset(productRepository);
        Mockito.reset(managerRepository);
    }

    @Test
    void createProduct() {
        when(productMapper.productDtoToProduct(TestData.PRODUCT_DTO)).thenReturn(TestData.PRODUCT);
        when(productRepository.save(TestData.PRODUCT)).thenReturn(TestData.PRODUCT);
        when(productMapper.productToProductDto(TestData.PRODUCT)).thenReturn(TestData.PRODUCT_DTO);
        ProductDto result = productService.createProduct(TestData.PRODUCT_DTO);
        assertNotNull(result);
        assertEquals(TestData.PRODUCT_DTO, result);
    }

    @Test
    void createProduct_Negative_RuntimeException() {
        when(productMapper.productDtoToProduct(TestData.PRODUCT_DTO)).thenReturn(TestData.PRODUCT);
        when(productRepository.save(TestData.PRODUCT)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> productService.createProduct(TestData.PRODUCT_DTO));
    }

    @Test
    void getProductById() {
        long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(TestData.PRODUCT));
        when(productMapper.productToProductDto(TestData.PRODUCT)).thenReturn(TestData.PRODUCT_DTO);
        ProductDto result = productService.getProductById(productId);
        assertNotNull(result);
        assertEquals(TestData.PRODUCT_DTO.getId(), result.getId());
        assertEquals(TestData.PRODUCT_DTO.getName(), result.getName());
        assertEquals(TestData.PRODUCT_DTO.getProductType(), result.getProductType());
    }

    @Test
    void getProductById_NegativeCase_ProductNotFound() {
        long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> productService.getProductById(productId));
    }

    @Test
    void updateProductById() {
        long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(TestData.PRODUCT_UPDATED));
        when(managerRepository.getReferenceById(TestData.PRODUCT_DTO_UPDATED.getManagerId())).thenReturn(TestData.MANAGER);
        when(productRepository.save(TestData.PRODUCT_UPDATED)).thenReturn(TestData.PRODUCT_UPDATED);
        when(productMapper.productToProductDto(TestData.PRODUCT_UPDATED)).thenReturn(TestData.PRODUCT_DTO_UPDATED);

        ProductDto result = productService.updateProductById(productId, TestData.PRODUCT_DTO_UPDATED);
        assertNotNull(result);
        assertEquals(TestData.PRODUCT_DTO_UPDATED.getName(), result.getName());
    }

    @Test
    void updateProductById_NegativeCase_ProductNotFound() {
        long productId = 1L;
        ProductDto updatedProductDto = TestData.PRODUCT_DTO;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> productService.updateProductById(productId, updatedProductDto));
    }

    @Test
    void deleteProductById() {
        long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(TestData.PRODUCT));
        when(productRepository.save(TestData.PRODUCT)).thenReturn(TestData.PRODUCT);
        productService.deleteProductById(productId);
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(TestData.PRODUCT);
        assertEquals(StatusType.REMOVED, TestData.PRODUCT.getStatusType());
    }

    @Test
    void deleteProductById_NegativeCase_ProductNotFound() {
        long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> productService.deleteProductById(productId));
        verify(productRepository, never()).save(any());
    }

    @Test
    void getAllProductsWhereStatusTypeIs() {
        StatusType status = StatusType.ACTIVE;
        when(productRepository.findAllByStatusType(status)).thenReturn(List.of(TestData.PRODUCT));
        when(productMapper.productToProductDto(TestData.PRODUCT)).thenReturn(TestData.PRODUCT_DTO);
        List<ProductDto> result = productService.getAllProductsWhereStatusTypeIs(status);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TestData.PRODUCT_DTO, result.get(0));
    }

    @Test
    void getAllProductsWhereStatusTypeIs_NegativeCase_EmptyList() {
        StatusType status = StatusType.ACTIVE;
        when(productRepository.findAllByStatusType(status)).thenReturn(new ArrayList<>());

        List<ProductDto> result = productService.getAllProductsWhereStatusTypeIs(status);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllProductsByProductTypeAndCurrencyCode() {
        ProductType type = ProductType.DEBIT_ACCOUNT;
        CurrencyCode code = CurrencyCode.EUR;
        when(productRepository.findAllByProductTypeAndCurrencyCode(type, code)).thenReturn(List.of(TestData.PRODUCT));
        when(productMapper.productToProductDto(TestData.PRODUCT)).thenReturn(TestData.PRODUCT_DTO);
        List<ProductDto> result = productService.getAllProductsByProductTypeAndCurrencyCode(type, code);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TestData.PRODUCT_DTO, result.get(0));
    }

    @Test
    void getAllProductsByProductTypeAndCurrencyCode_NegativeCase_EmptyList() {
        ProductType type = ProductType.CREDIT_ACCOUNT;
        CurrencyCode code = CurrencyCode.EUR;
        when(productRepository.findAllByProductTypeAndCurrencyCode(type, code)).thenReturn(new ArrayList<>());

        List<ProductDto> result = productService.getAllProductsByProductTypeAndCurrencyCode(type, code);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}