package de.telran.SpringTechnologyBankApp.dtos.usersapp;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"login", "password"})
@ToString(of = {"login", "password"})
public class UserApplicationDto {

//    private Long id;

    private String login;

    private String password;

    private String sessionToken;

    private int sessionExpiryMinutes;
}
