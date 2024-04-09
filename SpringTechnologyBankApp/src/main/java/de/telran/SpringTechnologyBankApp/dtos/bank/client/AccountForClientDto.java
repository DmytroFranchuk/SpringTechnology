package de.telran.SpringTechnologyBankApp.dtos.bank.client;

import de.telran.SpringTechnologyBankApp.entities.enums.AccountType;
import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name", "balance"})
public class AccountForClientDto {
    private Long id;
    private String name;
    private BigDecimal balance;
    private StatusType statusAccount;
    private AccountType accountType;
    private CurrencyCode currencyCode;
}
