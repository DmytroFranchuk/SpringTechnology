package de.telran.SpringTechnologyBankApp.dtos.bank.account;

import de.telran.SpringTechnologyBankApp.entities.enums.AccountType;
import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name", "balance", "accountType"})
public class AccountDto {
    private Long id;
    private String name;
    private BigDecimal balance;
    private StatusType statusAccount;
    private AccountType accountType;
    private CurrencyCode currencyCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long clientId;
    private Set<AgreementForAccountDto> agreements = new HashSet<>();
    private Set<TransactionForAccountDto> debitTransactions = new HashSet<>();
    private Set<TransactionForAccountDto> creditTransactions = new HashSet<>();


    @Override
    public String toString() {
        return "\nAccountDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", statusAccount=" + statusAccount +
                ", accountType=" + accountType +
                ", currencyCode=" + currencyCode +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", clientId=" + clientId +
                ", agreements=" + agreements +
                ", debitTransactions=" + debitTransactions +
                ", creditTransactions=" + creditTransactions +
                '}';
    }
}