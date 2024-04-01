package de.telran.SpringTechnologyBankApp.entities.bank;

import de.telran.SpringTechnologyBankApp.entities.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"amount", "description", "currencyCode"})
@EqualsAndHashCode(of = {"amount", "currencyCode"})
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "idem_key")
    private String idempotencyKey;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Column(name = "curr_code")
    private CurrencyCode currencyCode;

    @Column(name = "trans_type")
    private TransactionType transactionType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account debitAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account creditAccount;
}