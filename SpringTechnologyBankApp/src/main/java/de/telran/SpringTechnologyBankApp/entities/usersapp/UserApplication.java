package de.telran.SpringTechnologyBankApp.entities.usersapp;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import de.telran.SpringTechnologyBankApp.entities.history.LoginUserHistory;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"login", "sessionToken"})
@Entity
@Table(name = "users_app")
public class UserApplication implements UserDetails {
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
    private LocalDateTime sessionExpiry;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleUserApplication> roles = new HashSet<>();

    @OneToMany(
            mappedBy = "userApplication",
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH},
            orphanRemoval = true
    )
    private Set<LoginUserHistory> userHistories = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "\nUserApplication{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                ", sessionExpiry=" + sessionExpiry +
                ", roles=" + roles +
                ", userHistories=" + userHistories +
                '}';
    }
}