package de.telran.SpringTechnologyBankApp.services.usersapp.interf;

import de.telran.SpringTechnologyBankApp.dtos.usersapp.RoleUserApplicationDto;
import de.telran.SpringTechnologyBankApp.dtos.usersapp.UserApplicationDto;
import de.telran.SpringTechnologyBankApp.entities.usersapp.RoleUserApplication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;
import java.util.Set;

public interface UserApplicationService
//        extends
//        UserDetails,
//        UserDetailsService
//        UserDetailsManager,
//        GrantedAuthority
{
    void addUser(UserApplicationDto userAppDto);
    public void initializeRoles();
    void addAllRoles(List<RoleUserApplication> roles);

    boolean hasAnyRole();



}
