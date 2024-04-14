package de.telran.SpringTechnologyBankApp.controllers.usersapp;

import de.telran.SpringTechnologyBankApp.dtos.usersapp.UserApplicationDto;
import de.telran.SpringTechnologyBankApp.entities.usersapp.UserApplication;
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
    public UserApplication getByName(@PathVariable Long id) {
        return userApplicationService.getUserId(id);
    }


    @PreAuthorize("hasRole('ROLE_REGISTRAR')")
    @GetMapping(value = "/all")
    public List<UserApplication> getAll() {
        return userApplicationService.getAll();
    }
}


//    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
//    @PreAuthorize("permitAll()")

//    @PostMapping("/addUser")
//    public ResponseEntity<String> addNewUser(@RequestBody @NotNull UserAndManagerDto userAndManagerDto) {
//        UserApplicationDto userDto = userAndManagerDto.getUserDto();
//        ManagerDto managerDto = userAndManagerDto.getManagerDto();
//        String login = userDto.getLogin();
//        String password = userDto.getPassword();
//        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login and password are required fields");
//        }
//        RoleType roleUser = userDto.getRole();
//        if (roleUser == null || !Arrays.asList(RoleType.values()).contains(roleUser)) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role type: " + roleUser);
//        }
//        userDto.setSessionToken(request.getHeader("Host"));
//        userDto.setSessionExpiry(request.getContentLength());
//        try {
//            userApplicationService.addUser(userDto);
//            return ResponseEntity.ok("User added successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add user");
//        }
//    }

//    @GetMapping("/manager/{id}")
//    public ResponseEntity<Object> getAnyObject(@PathVariable Long id) {
//        Manager manager = new Manager(1L, "John", "Doe", "john.doe",
//                "password", "john@example.com", "Manager description",
//                StatusType.ACTIVE, RoleType.ROLE_MANAGER,
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                new HashSet<>(), new HashSet<>()
//        );
//
//        Client client1 = new Client();
//        Client client2 = new Client();
//
//        client1.setId(1L);
//        client2.setId(2L);
//
//        client1.setTaxNumber("12345-FE");
//        client2.setTaxNumber("23134-SE");
//
//
//
//
//        manager.addClient(client1);
//        manager.addClient(client2);
//
//        Product product1 = new Product();
//        Product product2 = new Product();
//        Product product3 = new Product();
//        Product product4 = new Product();
//        product1.setId(1L);
//        product2.setId(2L);
//        product3.setId(3L);
//        product4.setId(4L);
//        product1.setLimitSum(new BigDecimal(1111));
//        product2.setLimitSum(new BigDecimal(2222));
//        product3.setLimitSum(new BigDecimal(3333));
//        product4.setLimitSum(new BigDecimal(4444));
//        manager.addProduct(product1);
//        manager.addProduct(product2);
//        manager.addProduct(product3);
//        manager.addProduct(product4);
//
//        System.out.println(manager);
//
//        ManagerDto managerDto = managerMapper.managerToManagerDto(manager);
//        System.out.println(managerDto);
//
//        return ResponseEntity.ok(managerDto);
//    }
