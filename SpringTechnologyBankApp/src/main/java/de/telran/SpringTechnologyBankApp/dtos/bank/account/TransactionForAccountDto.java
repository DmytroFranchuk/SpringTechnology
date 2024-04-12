package de.telran.SpringTechnologyBankApp.dtos.bank.account;

import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.TransactionType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"currencyCode", "transactionType"})
@ToString(of = {"currencyCode", "transactionType"})
public class TransactionForAccountDto {
    private Long id;
    private CurrencyCode currencyCode;
    private TransactionType transactionType;
}
