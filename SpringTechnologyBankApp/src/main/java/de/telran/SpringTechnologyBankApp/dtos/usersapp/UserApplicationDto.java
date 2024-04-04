package de.telran.SpringTechnologyBankApp.dtos.usersapp;

import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import de.telran.SpringTechnologyBankApp.entities.history.LoginUserHistory;
import de.telran.SpringTechnologyBankApp.entities.usersapp.RoleUserApplication;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"login", "password"})
public class UserApplicationDto {
    private String login;
    private String password;
    private RoleType role;
    private String sessionToken;
    private int sessionExpiryMinutes;

    @Override
    public String toString() {
        return "\nUserApplicationDto{" +
                "login='" + login + '\'' +
                ", role=" + role +
                '}';
    }
}
