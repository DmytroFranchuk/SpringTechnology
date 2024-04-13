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
@EqualsAndHashCode(of = {"amount", "currencyCode"})
public class TransactionDto {
    private Long id;
    private String idempotencyKey;
    private BigDecimal amount;
    private String description;
    private CurrencyCode currencyCode;
    private TransactionType transactionType;
    private LocalDateTime createdAt;
    private Long senderAccountId;
    private Long recipientAccountId;

    @Override
    public String toString() {
        return "\nTransactionDto{" +
                "id=" + id +
                ", idempotencyKey='" + idempotencyKey + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", currencyCode=" + currencyCode +
                ", transactionType=" + transactionType +
                ", createdAt=" + createdAt +
                ", senderAccountId=" + senderAccountId +
                ", recipientAccountId=" + recipientAccountId +
                '}';
    }
}