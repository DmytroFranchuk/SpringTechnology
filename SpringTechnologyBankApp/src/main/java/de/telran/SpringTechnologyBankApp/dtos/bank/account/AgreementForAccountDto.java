package de.telran.SpringTechnologyBankApp.dtos.bank.account;

import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"sum", "currencyCode"})
public class AgreementForAccountDto {
    private Long id;
    private BigDecimal interestRate;
    private BigDecimal sum;
    private CurrencyCode currencyCode;
    private StatusType statusType;

    @Override
    public String toString() {
        return "\nAgreementForAccountDto{" +
                "id=" + id +
                ", interestRate=" + interestRate +
                ", sum=" + sum +
                ", currencyCode=" + currencyCode +
                ", statusType=" + statusType +
                '}';
    }
}
