package de.telran.SpringTechnologyBankApp.entities.bank;

import de.telran.SpringTechnologyBankApp.entities.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"limitSum", "currencyCode", "interestRate"})
@ToString(of = {"name", "productType", "interestRate", "limitSum"})
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status_type")
    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @Column(name = "product_type")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(name = "curr_code")
    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "limit_sum")
    private BigDecimal limitSum;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Manager manager;

    @OneToMany(
            mappedBy = "product",
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH},
            orphanRemoval = true
    )
    private Set<Agreement> agreements = new HashSet<>();

    public void addAgreement(Agreement agreement) {
        agreements.add(agreement);
        agreement.setProduct(this);
    }

    public void removeAgreement(Agreement agreement) {
        agreements.remove(agreement);
        agreement.setProduct(null);
    }
}