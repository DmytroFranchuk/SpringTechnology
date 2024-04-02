package de.telran.SpringTechnologyBankApp.dtos.history;

import de.telran.SpringTechnologyBankApp.dtos.usersapp.UserApplicationDto;
import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"loginTime", "userId", "ipAddress"})
@EqualsAndHashCode(of = {"loginTime", "userId", "ipAddress"})
public class LoginUserHistoryDto {

    private Long id;

    private String userId;

    private LocalDateTime loginTime;

    private LocalDateTime logoutTime;

    private String ipAddress;

    private String sessionToken;

    private int sessionExpiry;

    private RoleType roleType;

    private UserApplicationDto userApplication;
}