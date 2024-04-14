package de.telran.SpringTechnologyBankApp.services.usersapp.interf;

import de.telran.SpringTechnologyBankApp.dtos.usersapp.RoleUserApplicationDto;
import de.telran.SpringTechnologyBankApp.dtos.usersapp.UserApplicationDto;
import de.telran.SpringTechnologyBankApp.entities.usersapp.RoleUserApplication;
import de.telran.SpringTechnologyBankApp.entities.usersapp.UserApplication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserApplicationService {
    boolean hasAnyRole();
    void initializeRoles();
    List<UserApplication> getAll();
    UserApplication getUserId(Long id);
    void addUser(UserApplicationDto userAppDto);
//    UserDetails loadUserByUsername(String username);
    void addAllRoles(List<RoleUserApplication> roles);

}

//, UserDetailsManager
