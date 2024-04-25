package de.telran.SpringTechnologyBankApp.controllers.registrationusers;

import de.telran.SpringTechnologyBankApp.dtos.usersapp.UserApplicationDto;
import de.telran.SpringTechnologyBankApp.services.usersapp.interf.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registration/users")
@RequiredArgsConstructor
public class UserRegistrationController {
    private final UserApplicationService userApplicationService;

    @PreAuthorize("hasRole('ROLE_REGISTRAR')")
    @PostMapping(value = "add")
    public ResponseEntity<String> addNewUser(@RequestBody UserApplicationDto userAppDto) {
        userApplicationService.addUser(userAppDto);
        return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    }

    @PreAuthorize("hasRole('ROLE_REGISTRAR')")
    @GetMapping(value = "/{id}")
    public UserApplicationDto getByName(@PathVariable Long id) {
        return userApplicationService.getUserById(id);
    }

    @PreAuthorize("hasRole('ROLE_REGISTRAR')")
    @GetMapping(value = "/all")
    public List<UserApplicationDto> getAll() {
        return userApplicationService.getUsers();
    }

    @PreAuthorize("hasRole('ROLE_REGISTRAR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        if (userApplicationService.deleteUserById(id)) {
            return ResponseEntity.ok("User with id " + id + " has been successfully deleted");
        }
        return ResponseEntity.notFound().build();
    }
}