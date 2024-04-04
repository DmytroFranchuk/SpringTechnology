package de.telran.SpringTechnologyBankApp.entities.bank;

import de.telran.SpringTechnologyBankApp.entities.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name", "balance", "accountType"})
@ToString(of = {"name", "accountType", "statusAccount"})
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "status_type")
    @Enumerated(EnumType.STRING)
    private StatusType statusAccount;

    @Column(name = "acct_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "curr_code")
    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "account_agreement",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "agreement_id")
    )
    private Set<Agreement> agreements = new HashSet<>();

//    public void addAgreement(Agreement agreement) {
//        agreements.add(agreement);
//        agreement.getAccounts().add(this);
//    }
//
//    public void removeAgreement(Agreement agreement) {
//        agreements.remove(agreement);
//        agreement.getAccounts().remove(this);
//    }

    @OneToMany(
            mappedBy = "debitAccount",
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH},
            orphanRemoval = true
    )
    private Set<Transaction> debitTransactions = new HashSet<>();

    public void addDebitAccount(Transaction debitAccount) {
        debitTransactions.add(debitAccount);
        debitAccount.setDebitAccount(this);
    }

    public void removeDebitAccount(Transaction debitAccount) {
        debitTransactions.remove(debitAccount);
        debitAccount.setDebitAccount(null);
    }

    @OneToMany(
            mappedBy = "creditAccount",
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH},
            orphanRemoval = true
    )
    private Set<Transaction> creditTransactions = new HashSet<>();

    public void addCreditAccount(Transaction creditAccount) {
        creditTransactions.add(creditAccount);
        creditAccount.setCreditAccount(this);
    }

    public void removeCreditAccount(Transaction creditAccount) {
        creditTransactions.remove(creditAccount);
        creditAccount.setCreditAccount(null);
    }
}