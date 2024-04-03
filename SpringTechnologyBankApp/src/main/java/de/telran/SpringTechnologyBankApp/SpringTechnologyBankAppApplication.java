package de.telran.SpringTechnologyBankApp;

import de.telran.SpringTechnologyBankApp.services.usersapp.interf.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@RequiredArgsConstructor
public class SpringTechnologyBankAppApplication implements CommandLineRunner {

    private final UserApplicationService userApplicationService;

    public static void main(String[] args) {
        SpringApplication.run(SpringTechnologyBankAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userApplicationService.initializeRoles();

    }
}
