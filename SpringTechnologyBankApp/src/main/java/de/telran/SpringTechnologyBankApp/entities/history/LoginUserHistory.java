package de.telran.SpringTechnologyBankApp.entities.history;

import de.telran.SpringTechnologyBankApp.entities.usersapp.UserApplication;
import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"loginTime", "userId", "ipAddress"})
@EqualsAndHashCode(of = {"loginTime", "userId", "ipAddress"})
@Entity
@Table(name = "users_login_history")
public class LoginUserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "login_time", nullable = false, updatable = false)
    private LocalDateTime loginTime;

    @Column(name = "logout_time", nullable = false, updatable = false)
    private LocalDateTime logoutTime;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "session_token")
    private String sessionToken;

    @Column(name = "session_expiry")
    private int sessionExpiry;

    @Column(name = "role_type")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserApplication userApplication;
}