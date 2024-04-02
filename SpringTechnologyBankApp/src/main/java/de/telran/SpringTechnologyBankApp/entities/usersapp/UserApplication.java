package de.telran.SpringTechnologyBankApp.entities.usersapp;

import de.telran.SpringTechnologyBankApp.entities.history.LoginUserHistory;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"login", "roles"})
@ToString(of = {"login", "roles"})
@Entity
@Table(name = "users_app")
public class UserApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "user_login")
    private String login;

    @Column(name = "user_password")
    private String password;

    @Column(name = "session_token")
    private String sessionToken;

    @Column(name = "session_expiry")
    private int sessionExpiryMinutes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleUserApplication> roles;

    public void addRoleUserApplication(RoleUserApplication role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRoleUserApplication(RoleUserApplication role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }

    @OneToMany(
            mappedBy = "userApplication",
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH},
            orphanRemoval = true
    )
    private Set<LoginUserHistory> userHistories = new HashSet<>();

    public void addClient(LoginUserHistory userHistory) {
        userHistories.add(userHistory);
        userHistory.setUserApplication(this);
    }

    public void removeClient(LoginUserHistory userHistory) {
        userHistories.remove(userHistory);
        userHistory.setUserApplication(null);
    }
}
