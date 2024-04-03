package de.telran.SpringTechnologyBankApp.dtos.usersapp;

import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"login", "password"})
@ToString(of = {"login", "password"})
public class UserApplicationDto {

    private String login;

    private String password;

    private RoleType role;

    private String sessionToken;

    private int sessionExpiryMinutes;
}
