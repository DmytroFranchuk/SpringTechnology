package de.telran.SpringTechnologyBankApp.controllers.bank;

import de.telran.SpringTechnologyBankApp.dtos.bank.product.ProductDto;
import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.services.bank.interf.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProductById(@Valid
            @PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProductById(id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> updateProductByIdPatch(@Valid
            @PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        return updateProductById(id, productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProductById(@Valid @PathVariable("id") Long id) {
        productService.deleteProductById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", String.format("Продукт с id %d успешно удален", id));
        return ResponseEntity.ok(response);
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProductDto>> getAllProductsByStatus(@Valid @PathVariable("status") StatusType status) {
        List<ProductDto> products = productService.getAllProductsWhereStatusTypeIs(status);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/type/{type}/currency/{code}")
    public ResponseEntity<List<ProductDto>> getAllProductsByProductTypeAndCurrencyCode(@Valid
            @PathVariable("type") ProductType type,
            @PathVariable("code") CurrencyCode code) {

        List<ProductDto> products = productService.getAllProductsByProductTypeAndCurrencyCode(type, code);
        return ResponseEntity.ok(products);
    }
}
