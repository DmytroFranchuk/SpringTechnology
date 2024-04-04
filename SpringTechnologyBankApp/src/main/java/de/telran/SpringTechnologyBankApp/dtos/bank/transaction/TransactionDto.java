package de.telran.SpringTechnologyBankApp.dtos.bank.transaction;

import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"amount", "description", "currencyCode"})
@EqualsAndHashCode(of = {"amount", "currencyCode"})
public class TransactionDto {
    private Long id;
    private String idempotencyKey;
    private BigDecimal amount;
    private String description;
    private CurrencyCode currencyCode;
    private TransactionType transactionType;
    private LocalDateTime createdAt;
}