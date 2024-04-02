package de.telran.SpringTechnologyBankApp.dtos.usersapp;

import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"roleType", "id"})
@ToString(of = {"roleType", "id"})
public class RoleUserApplicationDto {

    private Long id;

    private RoleType roleType;
}