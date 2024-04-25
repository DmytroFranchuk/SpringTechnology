package de.telran.SpringTechnologyBankApp;

import de.telran.SpringTechnologyBankApp.repositories.usersapp.RoleUserApplicationRepository;
import de.telran.SpringTechnologyBankApp.services.usersapp.interf.UserApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SpringTechnologyBankAppTest {
	@Autowired
	private RoleUserApplicationRepository roleRepository;

	@Autowired
	private UserApplicationService userApplicationService;


//	@Test
//	void contextLoads() {
//	}
}
