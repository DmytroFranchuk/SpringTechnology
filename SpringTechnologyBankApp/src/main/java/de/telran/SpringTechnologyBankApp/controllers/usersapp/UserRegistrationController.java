package de.telran.SpringTechnologyBankApp.controllers.usersapp;

import de.telran.SpringTechnologyBankApp.dtos.usersapp.UserApplicationDto;
import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import de.telran.SpringTechnologyBankApp.services.usersapp.impl.UserApplicationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class UserRegistrationController {

    private final UserApplicationServiceImpl userApplicationService;
    private final HttpServletRequest request;


    @PostMapping("/addUser")
    public ResponseEntity<String> addNewUser(@RequestBody @NotNull UserApplicationDto userDto) {
        String login = userDto.getLogin();
        String password = userDto.getPassword();
        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login and password are required fields");
        }
        RoleType roleUser = userDto.getRole();
        if (roleUser == null || !Arrays.asList(RoleType.values()).contains(roleUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role type: " + roleUser);
        }

        String host = request.getHeader("Host");
        int contentLength = request.getContentLength();
        try {
            userApplicationService.addUser(new UserApplicationDto(login, password, roleUser, host, contentLength));
            return ResponseEntity.ok("User added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add user");
        }
    }
}