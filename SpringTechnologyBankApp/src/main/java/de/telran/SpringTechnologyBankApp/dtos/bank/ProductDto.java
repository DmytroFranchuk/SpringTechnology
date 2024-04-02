package de.telran.SpringTechnologyBankApp.dtos.bank;

import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"limitSum", "currencyCode", "interestRate"})
@ToString(of = {"name", "productType", "interestRate", "limitSum"})
public class ProductDto {
    private Long id;
    private String name;
    private StatusType statusType;
    private ProductType productType;
    private CurrencyCode currencyCode;
    private BigDecimal interestRate;
    private BigDecimal limitSum;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}