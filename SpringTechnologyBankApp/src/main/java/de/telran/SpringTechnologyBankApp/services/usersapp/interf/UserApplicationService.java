package de.telran.SpringTechnologyBankApp.services.usersapp.interf;

import de.telran.SpringTechnologyBankApp.dtos.usersapp.UserApplicationDto;
import de.telran.SpringTechnologyBankApp.entities.usersapp.RoleUserApplication;

import java.util.List;

public interface UserApplicationService {
    boolean hasAnyRole();
    void initializeRoles();
    List<UserApplicationDto> getUsers();
    UserApplicationDto getUserById(Long id);
    void addUser(UserApplicationDto userAppDto);
    void addAllRoles(List<RoleUserApplication> roles);
}
