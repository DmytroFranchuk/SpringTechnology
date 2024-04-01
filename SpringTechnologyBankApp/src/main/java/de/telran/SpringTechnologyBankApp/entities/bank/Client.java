package de.telran.SpringTechnologyBankApp.entities.bank;

import de.telran.SpringTechnologyBankApp.entities.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"taxNumber", "firstName", "phone"})
@ToString(of = {"firstName", "lastName"})
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "tax_number")
    private String taxNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status_type")
    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @Column(name = "role_type")
    @Enumerated(EnumType.STRING)
    private RoleType roleType = RoleType.ROLE_CLIENT;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Manager manager;

    @OneToMany(
            mappedBy = "client",
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH},
            orphanRemoval = true
    )
    private Set<Account> accounts = new HashSet<>();

    public void addAccount(Account account) {
        accounts.add(account);
        account.setClient(this);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        account.setClient(null);
    }
}