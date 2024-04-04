package de.telran.SpringTechnologyBankApp.dtos.usersapp;

import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"roleType"})
@ToString(of = {"roleType"})
public class RoleUserApplicationDto {
    private RoleType roleType;
}