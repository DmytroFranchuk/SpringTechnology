package de.telran.SpringTechnologyBankApp.dtos.bank.agreement;

import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"interestRate", "statusType", "sum"})
@ToString(of = {"interestRate", "statusType", "sum"})
public class AgreementDto {
    private Long id;
    private BigDecimal interestRate;
    private BigDecimal sum;
    private CurrencyCode currencyCode;
    private StatusType statusType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
