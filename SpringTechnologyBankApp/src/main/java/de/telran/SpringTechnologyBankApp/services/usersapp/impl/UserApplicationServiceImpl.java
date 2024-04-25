package de.telran.SpringTechnologyBankApp.services.usersapp.impl;

import de.telran.SpringTechnologyBankApp.dtos.usersapp.UserApplicationDto;
import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import de.telran.SpringTechnologyBankApp.entities.usersapp.RoleUserApplication;
import de.telran.SpringTechnologyBankApp.entities.usersapp.UserApplication;
import de.telran.SpringTechnologyBankApp.exceptions.NotDeletionEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotExistEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.mappers.usersapp.UserApplicationMapper;
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
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {
    private final RoleUserApplicationRepository roleUserApplicationRepository;
    private final UserApplicationRepository userApplicationRepository;
    private final UserApplicationMapper userApplicationMapper;
    private final PasswordEncoder passwordEncoder;


    public UserApplicationDto getUserById(Long id) {
        UserApplication user = userApplicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Не найден пользователь с id: " + id));
        return userApplicationMapper.userApplicationToDto(user);
    }

    public List<UserApplicationDto> getUsers() {
        List<UserApplication> users = userApplicationRepository.findAll();
        return users.stream()
                .map(userApplicationMapper::userApplicationToDto)
                .collect(Collectors.toList());
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

    public boolean deleteUserById(Long id) {
        Optional<UserApplication> user = userApplicationRepository.findById(id);
        if (user.isPresent()) {
            userApplicationRepository.deleteById(id);
            return true;
        } else {
            throw new NotDeletionEntityException("User with id " + id + " not found");
        }
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
