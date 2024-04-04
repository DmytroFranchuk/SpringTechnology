package de.telran.SpringTechnologyBankApp.dtos.usersapp;

import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ManagerDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserAndManagerDto {
    private UserApplicationDto userDto;
    private ManagerDto managerDto;
}
