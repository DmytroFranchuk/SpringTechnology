package de.telran.SpringTechnologyBankApp.dtos.bank.transaction;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"amount", "currencyCode"})
public class TransactionResponseDto {
    private Long id;
    private String idempotencyKey;
    private BigDecimal amount;
    private String description;
    private String currencyCode;
    private String transactionType;
    private LocalDateTime createdAt;
    private Long senderAccountId;
    private BigDecimal senderAccountBalance;
    private Long recipientAccountId;
    private BigDecimal recipientAccountBalance;
}
