package de.telran.SpringTechnologyBankApp.entities.bank;

import de.telran.SpringTechnologyBankApp.entities.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"firstName", "lastName", "description"})
@EqualsAndHashCode(of = {"firstName", "lastName", "statusType"})
@Entity
@Table(name = "managers")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "description")
    private String description;

    @Column(name = "status_type")
    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @Column(name = "role_type")
    @Enumerated(EnumType.STRING)
    private RoleType roleType = RoleType.ROLE_MANAGER;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "manager",
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH},
            orphanRemoval = true
    )
    private Set<Client> clients = new HashSet<>();

    public void addClient(Client client) {
        clients.add(client);
        client.setManager(this);
    }

    public void removeClient(Client client) {
        clients.remove(client);
        client.setManager(null);
    }

    @OneToMany(
            mappedBy = "manager",
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH},
            orphanRemoval = true
    )
    private Set<Product> products = new HashSet<>();

    public void addProduct(Product product) {
        products.add(product);
        product.setManager(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.setManager(null);
    }
}