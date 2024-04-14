package de.telran.SpringTechnologyBankApp.services.usersapp.impl;

import de.telran.SpringTechnologyBankApp.dtos.usersapp.UserApplicationDto;
import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import de.telran.SpringTechnologyBankApp.entities.usersapp.RoleUserApplication;
import de.telran.SpringTechnologyBankApp.entities.usersapp.UserApplication;
import de.telran.SpringTechnologyBankApp.exceptions.NotExistEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.repositories.usersapp.RoleUserApplicationRepository;
import de.telran.SpringTechnologyBankApp.repositories.usersapp.UserApplicationRepository;
import de.telran.SpringTechnologyBankApp.services.usersapp.interf.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {
    private final RoleUserApplicationRepository roleUserApplicationRepository;
    private final UserApplicationRepository userApplicationRepository;
//    private final InMemoryUsersConfig inMemoryUsersConfig;
    private final PasswordEncoder passwordEncoder;


    public UserApplication getUserId(Long id) {
        return userApplicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Не найден пользователь с id: " + id));
    }

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
        LocalDateTime expiryTime = LocalDateTime.now().plusDays(1);
        newUser.setSessionExpiry(expiryTime);
        userApplicationRepository.save(newUser);
    }

    public List<UserApplication> getAll() {
        return userApplicationRepository.findAll();
    }

    private RoleUserApplication validateRoleExists(RoleType roleType) {
        return roleUserApplicationRepository.findByRoleType(roleType)
                .orElseThrow(() -> new NotExistEntityException("Неверная роль пользователя: " + roleType));
    }

    public boolean hasAnyRole() {
        return roleUserApplicationRepository.count() > 0;
    }

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
}


//    @Override
//    // Создайте нового пользователя с предоставленными данными.
//    public void createUser(UserDetails user) {
//
//    }
//    @Override
//    // Измените пароль текущего пользователя.
//    public void changePassword(String oldPassword, String newPassword) {
//
//    }
//    @Override
//    // Обновите указанного пользователя.
//    public void updateUser(UserDetails user) {
//
//    }
//    @Override
//    // Удалите пользователя с данным именем входа из системы
//    public void deleteUser(String username) {
//
//    }
//    @Override
//    // Проверьте, существует ли в системе пользователь с указанным именем входа
//    public boolean userExists(String username) {
//        return false;
//    }

//=====================================================================================

//    @Override
//    public String getAuthority() {
//        return null;
//    }

//    @Override
//    public boolean isEnabled() {
//        // true если пользователь включен, false в противном случае
//        return true;
//    }
//    @Override
//    public boolean isCredentialsNonExpired() {
//        // true действительны ли учетные данные пользователя (т. е. не истекли),
//        // false если они больше не действительны (т. е. истекли)
//        return true;
//    }
//    @Override
//    public boolean isAccountNonLocked() {
//        // true если пользователь не заблокирован, false иначе
//        return true;
//    }
//    @Override
//    public boolean isAccountNonExpired() {
//        // true действительна ли учетная запись пользователя (т.е. срок ее действия не истек),
//        // false если она больше не действительна (т.е. срок ее действия истек)
//        return true;
//    }
//    @Override
//    public String getUsername() {
//        // Возвращает имя пользователя, используемое для аутентификации пользователя. Не могу вернуться null.
//        return null;
//    }
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // Возвращает полномочия, предоставленные пользователю. Не могу вернуться null.
//        return null;
//    }
//    @Override
//    public String getPassword() {
//        // Возвращает пароль, используемый для аутентификации пользователя
//        return null;
//    }


//        UserApplicationDto userDto = new UserApplicationDto();
//        userDto.setLogin("superUserApp");
//        userDto.setPassword("1111");
//        userDto.setRole(RoleType.valueOf("ROLE_REGISTRAR"));
//        addUser(userDto);
