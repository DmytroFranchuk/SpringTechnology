package de.telran.SpringTechnologyBankApp.entities.bank;

import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"interestRate", "statusType", "sum"})
@ToString(of = {"interestRate", "statusType", "sum"})
@DynamicUpdate
@Entity
@Table(name = "agreements")
public class Agreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "sum_value")
    private BigDecimal sum;

    @Column(name = "curr_code")
    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;

    @Column(name = "status_type")
    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "agreements")
    private Set<Account> accounts = new HashSet<>();

    public void addAccount(Account account) {
        accounts.add(account);
        account.getAgreements().add(this);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        account.getAgreements().remove(this);
    }

    // Связь_многие_к_одному (договор-счет)
    // В_приложении_бизнес-модель_построена_как_многие_ко_многим
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Account account;
}
