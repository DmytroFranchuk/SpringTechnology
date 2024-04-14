package de.telran.SpringTechnologyBankApp.entities.usersapp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"roleType"})
@ToString(of = {"roleType"})
@Entity
@Table(name = "roles")
public class RoleUserApplication implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "role_type")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private Set<UserApplication> users = new HashSet<>();

    @Override
    public String getAuthority() {
        return roleType.name();
    }

//    public void addUserApplication(UserApplication userApplication) {
//        if (users.add(userApplication)) {
//            userApplication.getRoles().add(this);
//        }
//    }

//    public void removeUserApplication(UserApplication userApplication) {
//        users.remove(userApplication);
//        userApplication.getRoles().remove(this);
//    }
}