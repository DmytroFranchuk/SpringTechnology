package de.telran.SpringTechnologyBankApp.services.usersapp.impl;

import de.telran.SpringTechnologyBankApp.dtos.usersapp.UserApplicationDto;
import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import de.telran.SpringTechnologyBankApp.entities.usersapp.RoleUserApplication;
import de.telran.SpringTechnologyBankApp.entities.usersapp.UserApplication;
import de.telran.SpringTechnologyBankApp.exceptions.NotExistEntityException;
import de.telran.SpringTechnologyBankApp.repositories.usersapp.RoleUserApplicationRepository;
import de.telran.SpringTechnologyBankApp.repositories.usersapp.UserApplicationRepository;
import de.telran.SpringTechnologyBankApp.services.usersapp.interf.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {
    private final UserApplicationRepository userApplicationRepository;
    private final RoleUserApplicationRepository roleUserApplicationRepository;
    private final PasswordEncoder passwordEncoder;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserApplication userApp = userApplicationRepository.findByLogin(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Нет пользователя по имени: " + username));
//        return User.builder()
//                .username(userApp.getLogin())
//                .password(userApp.getPassword())
//                .roles(userApp.getRoles().stream().map(role -> role.getRoleType().toString()).toArray(String[]::new))
//                .build();
//    }

    @Override
    public void addUser(UserApplicationDto userAppDto) {
        if (userApplicationRepository.existsByLogin(userAppDto.getLogin())) {
            throw new NotExistEntityException("Пользователь логином " + userAppDto.getLogin() + " уже существует");
        }
        UserApplication newUser = new UserApplication();
        RoleUserApplication role = validateRoleExists(userAppDto.getRole());
        newUser.getRoles().add(role);
        newUser.setLogin(userAppDto.getLogin());
        newUser.setPassword(passwordEncoder.encode(userAppDto.getPassword()));
        newUser.setSessionToken(UUID.randomUUID().toString());
        newUser.setSessionExpiry(LocalDateTime.now().plusDays(1));
        userApplicationRepository.save(newUser);
    }

    private RoleUserApplication validateRoleExists(RoleType roleType) {
        return roleUserApplicationRepository.findByRoleType(roleType)
                .orElseThrow(() -> new NotExistEntityException("Неверная роль пользователя: " + roleType));
    }

    @Override
    public boolean hasAnyRole() {
        return roleUserApplicationRepository.count() > 0;
    }

    @Override
    public void addAllRoles(List<RoleUserApplication> roles) {
        roleUserApplicationRepository.saveAll(roles);
    }

    public void initializeRoles() {
        if (!hasAnyRole()) {
            List<RoleUserApplication> allRoles = new ArrayList<>();
            for (RoleType roleType : RoleType.values()) {
                RoleUserApplication role = new RoleUserApplication();
                role.setRoleType(roleType);
                allRoles.add(role);
            }
            addAllRoles(allRoles);
        }
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
