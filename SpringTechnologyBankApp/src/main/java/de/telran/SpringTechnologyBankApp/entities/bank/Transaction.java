package de.telran.SpringTechnologyBankApp.entities.bank;

import de.telran.SpringTechnologyBankApp.entities.enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"amount", "currencyCode"})
@DynamicUpdate
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
    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;

    @Column(name = "trans_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account debitAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account creditAccount;

    @Override
    public String toString() {
        return "\nTransaction{" +
                "id=" + id +
                ", idempotencyKey='" + idempotencyKey + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", currencyCode=" + currencyCode +
                ", transactionType=" + transactionType +
                ", createdAt=" + createdAt +
                ", debitAccount=" + debitAccount +
                ", creditAccount=" + creditAccount +
                '}';
    }
}