package de.telran.SpringTechnologyBankApp.services.usersapp.impl;

import de.telran.SpringTechnologyBankApp.configurations.securityWeb.DefaultUsersConfig;
import de.telran.SpringTechnologyBankApp.dtos.usersapp.RoleUserApplicationDto;
import de.telran.SpringTechnologyBankApp.dtos.usersapp.UserApplicationDto;
import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import de.telran.SpringTechnologyBankApp.entities.usersapp.RoleUserApplication;
import de.telran.SpringTechnologyBankApp.entities.usersapp.UserApplication;
import de.telran.SpringTechnologyBankApp.repositories.usersapp.RoleUserApplicationRepository;
import de.telran.SpringTechnologyBankApp.repositories.usersapp.UserApplicationRepository;
import de.telran.SpringTechnologyBankApp.services.usersapp.interf.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {
    private final UserApplicationRepository userApplicationRepository;
    private final RoleUserApplicationRepository roleUserApplicationRepository;

    public void saveUser(UserApplicationDto userAppDto) {
        if (userAppDto.getLogin() == null || userAppDto.getPassword() == null) {
            throw new IllegalArgumentException("Login and password must be provided");
        }
        Optional<UserApplication> existingUser = userApplicationRepository.findByLogin(userAppDto.getLogin());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User with this login already exists");
        }
        UserApplication newUser = new UserApplication();
        newUser.setLogin(userAppDto.getLogin());
        newUser.setPassword(userAppDto.getPassword());
        newUser.setSessionToken(userAppDto.getSessionToken());
        newUser.setSessionExpiryMinutes(userAppDto.getSessionExpiryMinutes());
        userApplicationRepository.save(newUser);
    }

    public void saveRole(RoleUserApplicationDto roleDto) {
        if (roleDto.getRoleType() == null) {
            throw new IllegalArgumentException("Role type must be provided");
        }
        if (!isValidRoleType(roleDto.getRoleType())) {
            throw new IllegalArgumentException("Invalid role type");
        }
        Optional<RoleUserApplication> existingRole = roleUserApplicationRepository.findByRoleType(roleDto.getRoleType());
        if (existingRole.isPresent()) {
            throw  new IllegalArgumentException("Role with this type already exists");
        }
        RoleUserApplication newRole = new RoleUserApplication();
        newRole.setRoleType(roleDto.getRoleType());
        roleUserApplicationRepository.save(newRole);
    }

    private boolean isValidRoleType(RoleType roleType) {
        for (RoleType validRole : RoleType.values()) {
            if (validRole.equals(roleType)) {
                return true;
            }
        }
        return false;
    }

































//    @Override
//    // Создайте нового пользователя с предоставленными данными.
//    public void createUser(UserDetails user) {
//
//    }
//
//    @Override
//    public String getUsername() {
//        // Возвращает имя пользователя, используемое для аутентификации пользователя. Не могу вернуться null.
//        return null;
//    }
//
//    @Override
//    public String getAuthority() {
//        return null;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // Возвращает полномочия, предоставленные пользователю. Не могу вернуться null.
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        // Возвращает пароль, используемый для аутентификации пользователя
//        return null;
//    }
//
//    @Override
//    // Измените пароль текущего пользователя.
//    public void changePassword(String oldPassword, String newPassword) {
//
//    }
//
//    @Override
//    // Обновите указанного пользователя.
//    public void updateUser(UserDetails user) {
//
//    }
//
//    @Override
//    // Удалите пользователя с данным именем входа из системы
//    public void deleteUser(String username) {
//
//    }
//
//
//    @Override
//    public boolean isAccountNonExpired() {
//        // true действительна ли учетная запись пользователя (т.е. срок ее действия не истек),
//        // false если она больше не действительна (т.е. срок ее действия истек)
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        // true если пользователь не заблокирован, false иначе
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        // true действительны ли учетные данные пользователя (т. е. не истекли),
//        // false если они больше не действительны (т. е. истекли)
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        // true если пользователь включен, false в противном случае
//        return true;
//    }
//
//    @Override
//    // Проверьте, существует ли в системе пользователь с указанным именем входа
//    public boolean userExists(String username) {
//        return false;
//    }
}
