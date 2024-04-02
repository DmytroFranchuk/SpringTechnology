package de.telran.SpringTechnologyBankApp.entities.usersapp;

import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"roleType", "users"})
@ToString(of = {"roleType", "users"})
@Entity
@Table(name = "roles")
public class RoleUserApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "role_type")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<UserApplication> users;

    public void addUserApplication(UserApplication userApplication) {
        users.add(userApplication);
        userApplication.getRoles().add(this);
    }

    public void removeUserApplication(UserApplication userApplication) {
        users.remove(userApplication);
        userApplication.getRoles().remove(this);
    }
}