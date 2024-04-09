package de.telran.SpringTechnologyBankApp;

import de.telran.SpringTechnologyBankApp.repositories.usersapp.RoleUserApplicationRepository;
import de.telran.SpringTechnologyBankApp.services.usersapp.interf.UserApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringTechnologyBankAppApplicationTests {
	@Autowired
	private RoleUserApplicationRepository roleRepository;

	@Autowired
	private UserApplicationService userApplicationService;

//
//	@Test
//	void contextLoads() {
//	}
}
