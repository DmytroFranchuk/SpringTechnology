package de.telran.SpringTechnologyBankApp.dtos.bank.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.NotFound;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name", "statusType", "limitSum"})
@ToString(of = {"name", "statusType", "limitSum"})
public class ProductDto {
    private Long id;
    private String name;
    private StatusType statusType;
    private ProductType productType;
    private CurrencyCode currencyCode;
    private BigDecimal interestRate;
    @Positive
    private BigDecimal limitSum;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Positive
    @NotNull
    @NotBlank
    @NotEmpty
    @NotFound
    private Long managerId;
    private Set<AgreementForProductDto> agreements = new HashSet<>();
}