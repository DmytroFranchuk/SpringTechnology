package de.telran.SpringTechnologyBankApp;

import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ManagerDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Manager;
import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.mappers.bank.ManagerMapper;
import de.telran.SpringTechnologyBankApp.services.usersapp.interf.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.HashSet;


@SpringBootApplication
@RequiredArgsConstructor
public class SpringTechnologyBankAppApplication implements CommandLineRunner {

    private final UserApplicationService userApplicationService;
//    private final ManagerMapper managerMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringTechnologyBankAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userApplicationService.initializeRoles();

//        Manager manager = new Manager(1L, "John", "Doe", "john.doe",
//                "password", "john@example.com", "Manager description",
//                StatusType.ACTIVE, RoleType.ROLE_MANAGER,
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                new HashSet<>(), new HashSet<>()
//        );
//        System.out.println(manager);

//        ManagerDto managerDto = managerMapper.managerToManagerDto(manager);
//        System.out.println(managerDto);
    }
}
