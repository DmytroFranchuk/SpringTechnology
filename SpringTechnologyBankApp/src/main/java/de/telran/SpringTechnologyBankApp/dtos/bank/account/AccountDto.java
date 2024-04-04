package de.telran.SpringTechnologyBankApp.dtos.bank.account;

import de.telran.SpringTechnologyBankApp.entities.enums.AccountType;
import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name", "balance", "accountType"})
@ToString(of = {"name", "accountType", "statusAccount"})
public class AccountDto {
    private Long id;
    private String name;
    private BigDecimal balance;
    private StatusType statusAccount;
    private AccountType accountType;
    private CurrencyCode currencyCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}