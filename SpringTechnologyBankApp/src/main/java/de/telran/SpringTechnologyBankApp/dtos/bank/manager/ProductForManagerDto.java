package de.telran.SpringTechnologyBankApp.dtos.bank.manager;

import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"statusType", "productType"})
public class ProductForManagerDto {
    private Long id;
    private StatusType statusType;
    private ProductType productType;
    private CurrencyCode currencyCode;
    private BigDecimal interestRate;
    private BigDecimal limitSum;

    @Override
    public String toString() {
        return "\nProductForManagerDto{" +
                "id=" + id +
                ", statusType=" + statusType +
                ", productType=" + productType +
                ", currencyCode=" + currencyCode +
                ", interestRate=" + interestRate +
                ", limitSum=" + limitSum +
                '}';
    }
}
