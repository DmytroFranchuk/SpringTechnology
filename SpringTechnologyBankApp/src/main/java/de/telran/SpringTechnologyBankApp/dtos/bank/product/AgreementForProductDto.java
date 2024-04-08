package de.telran.SpringTechnologyBankApp.dtos.bank.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.telran.SpringTechnologyBankApp.entities.bank.Account;
import de.telran.SpringTechnologyBankApp.entities.bank.Product;
import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"sum", "statusType"})
public class AgreementForProductDto {
    private Long id;
    private BigDecimal interestRate;
    private BigDecimal sum;
    private CurrencyCode currencyCode;
    private StatusType statusType;
}
