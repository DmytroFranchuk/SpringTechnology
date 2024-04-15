package de.telran.SpringTechnologyBankApp.controllers.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.SpringTechnologyBankApp.TestData;
import de.telran.SpringTechnologyBankApp.dtos.bank.product.ProductDto;
import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.services.bank.impl.ProductServiceImpl;
import de.telran.SpringTechnologyBankApp.services.usersapp.interf.UserApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "superUserApp", roles = "REGISTRAR")
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductServiceImpl productService;
    @MockBean
    private UserApplicationService userApplicationService;

    @Test
    void createProduct() throws Exception {
        when(productService.createProduct(eq(TestData.PRODUCT_DTO))).thenReturn(TestData.PRODUCT_DTO);
        mockMvc.perform(post("/api/v1/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.PRODUCT_DTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.managerId").value(TestData.PRODUCT_DTO.getManagerId()));
    }

    @Test
    void testCreateProduct_InvalidData() throws Exception {
        ProductDto invalidProductDto = new ProductDto();
        when(productService.createProduct(any())).thenThrow(DataIntegrityViolationException.class);
        mockMvc.perform(post("/api/v1/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProductDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateProduct_RuntimeException() throws Exception {
        ProductDto invalidProductDto = new ProductDto();
        when(productService.createProduct(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(post("/api/v1/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProductDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getProductById() throws Exception {
        Long productId = 1L;
        when(productService.getProductById(anyLong())).thenReturn(TestData.PRODUCT_DTO);
        mockMvc.perform(get("/api/v1/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(TestData.PRODUCT_DTO.getName()));
    }

    @Test
    void testGetProductById_Negative() throws Exception {
        Long productId = 1L;
        when(productService.getProductById(anyLong())).thenThrow(NotFoundEntityException.class);
        mockMvc.perform(get("/api/v1/products/{id}", productId))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateProductById() throws Exception {
        Long productId = 1L;
        when(productService.updateProductById(eq(productId), any())).thenReturn(TestData.PRODUCT_DTO);
        mockMvc.perform(put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.PRODUCT_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(TestData.PRODUCT_DTO.getName()));
    }

    @Test
    void testUpdateProductById_Negative_ProductNotFound() throws Exception {
        Long productId = 1L;
        when(productService.updateProductById(eq(productId), any())).thenThrow(NotFoundEntityException.class);
        mockMvc.perform(put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.PRODUCT_DTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateProductByIdPatch() throws Exception {
        Long productId = 1L;
        when(productService.updateProductById(eq(productId), any())).thenReturn(TestData.PRODUCT_DTO);
        mockMvc.perform(patch("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.PRODUCT_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(TestData.PRODUCT_DTO.getName()));
    }

    @Test
    void testUpdateProductByIdPatch_Negative_ProductNotFound() throws Exception {
        Long productId = 1L;
        when(productService.updateProductById(eq(productId), any())).thenThrow(NotFoundEntityException.class);
        mockMvc.perform(patch("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.PRODUCT_DTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProductById() throws Exception {
        Long productId = 1L;
        doNothing().when(productService).deleteProductById(eq(productId));
        mockMvc.perform(delete("/api/v1/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Продукт с id " + productId + " успешно удален"));
    }

    @Test
    void testDeleteProductById_Negative_ProductNotFound() throws Exception {
        Long productId = 1L;
        doThrow(NotFoundEntityException.class).when(productService).deleteProductById(eq(productId));
        mockMvc.perform(delete("/api/v1/products/{id}", productId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllProductsByStatus() throws Exception {
        StatusType status = StatusType.ACTIVE;
        when(productService.getAllProductsWhereStatusTypeIs(eq(status))).
                thenReturn(List.of(TestData.PRODUCT_DTO));
        mockMvc.perform(get("/api/v1/products/status/{status}", status))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(TestData.PRODUCT_DTO.getName()));
    }

    @Test
    void testGetAllProductsByStatus_Negative_ProductsNotFound() throws Exception {
        StatusType status = StatusType.ACTIVE;
        when(productService.getAllProductsWhereStatusTypeIs(eq(status)))
                .thenThrow(NotFoundEntityException.class);
        mockMvc.perform(get("/api/v1/products/status/{status}", status))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllProductsByProductTypeAndCurrencyCode() throws Exception {
        when(productService.getAllProductsByProductTypeAndCurrencyCode(eq(ProductType.DEBIT_ACCOUNT),
                eq(CurrencyCode.EUR))).thenReturn(List.of(TestData.PRODUCT_DTO));
        mockMvc.perform(get("/api/v1/products/type/{type}/currency/{code}",
                        ProductType.DEBIT_ACCOUNT, CurrencyCode.EUR))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(TestData.PRODUCT_DTO.getName()));
    }

    @Test
    void testGetAllProductsByProductTypeAndCurrencyCode_Negative_ProductsNotFound() throws Exception {
        when(productService.getAllProductsByProductTypeAndCurrencyCode(eq(ProductType.DEBIT_ACCOUNT),
                eq(CurrencyCode.EUR))).thenThrow(NotFoundEntityException.class);
        mockMvc.perform(get("/api/v1/products/type/{type}/currency/{code}",
                        ProductType.DEBIT_ACCOUNT, CurrencyCode.EUR))
                .andExpect(status().isNotFound());
    }
}