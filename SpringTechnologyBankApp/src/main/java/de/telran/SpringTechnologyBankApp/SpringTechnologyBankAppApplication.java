package de.telran.SpringTechnologyBankApp;

import de.telran.SpringTechnologyBankApp.services.usersapp.interf.UserApplicationService;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;


@SpringBootApplication
@RequiredArgsConstructor
public class SpringTechnologyBankAppApplication implements CommandLineRunner {

    private final UserApplicationService userApplicationService;
    private final DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(SpringTechnologyBankAppApplication.class, args);
    }

    @Override
    public void run(String... args) {
        userApplicationService.initializeRoles();
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(
                new ClassPathResource("db/data.sql"));
        databasePopulator.execute(dataSource);
    }
}
