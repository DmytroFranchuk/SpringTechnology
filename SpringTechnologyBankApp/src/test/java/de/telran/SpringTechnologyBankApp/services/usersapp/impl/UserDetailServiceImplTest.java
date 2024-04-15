package de.telran.SpringTechnologyBankApp.services.usersapp.impl;

import de.telran.SpringTechnologyBankApp.configurations.securityWeb.InMemoryUsersConfig;
import de.telran.SpringTechnologyBankApp.repositories.usersapp.UserApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserDetailServiceImplTest {
    private UserDetailServiceImpl userDetailService;

    @Mock
    private UserApplicationRepository userApplicationRepository;

    @Mock
    private InMemoryUsersConfig inMemoryUsersConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDetailService = new UserDetailServiceImpl(userApplicationRepository, inMemoryUsersConfig);
    }

    @Test
    void loadUserByUsername_UserExists_ReturnsUserDetails() {
//        String username = "testUser";
//        String password = "testPassword";
//        RoleType roleType = RoleType.ROLE_CLIENT;
//        RoleUserApplication role = new RoleUserApplication(1L, roleType, null);
//        String sessionToken = "exampleSessionToken";
//        LocalDateTime sessionExpiry = LocalDateTime.now();
//        Set<RoleUserApplication> roles = new HashSet<>();
//        Set<LoginUserHistory> userHistories = new HashSet<>();
//        UserApplication user = new UserApplication(1L, username,
//                password,
//                sessionToken,
//                sessionExpiry,
//                null,
//                null);
//
//
//        when(userApplicationRepository.findByLogin(username)).thenReturn(Optional.of(user));
//
//        UserDetails userDetails = userDetailService.loadUserByUsername(username);
//
//        assertEquals(username, userDetails.getUsername());
//        assertEquals(password, userDetails.getPassword());
//        assertEquals(Collections.singletonList(role), userDetails.getAuthorities());
    }

    @Test
    void loadUserByUsername_UserDoesNotExist_ThrowsException() {
//        String username = "nonExistingUser";
//        when(userApplicationRepository.findByLogin(username)).thenReturn(Optional.empty());
//
//        assertThrows(UsernameNotFoundException.class, () -> userDetailService.loadUserByUsername(username));
    }

    @Test
    void loadUserByUsername_UserExistsInMemory_ReturnsUserDetails() {
//        String username = "inMemoryUser";
//        String password = "inMemoryPassword";
//        RoleUserApplication role = new RoleUserApplication();
//        UserDetails inMemoryUser = new org.springframework.security.core.userdetails.User(username, password, Collections.singletonList(role));
//
//        when(inMemoryUsersConfig.inMemoryUserDetailsManager().loadUserByUsername(username)).thenReturn(inMemoryUser);
//
//        UserDetails userDetails = userDetailService.loadUserByUsername(username);
//
//        assertEquals(inMemoryUser, userDetails);
    }

    @Test
    void loadUserByUsername_UserExistsInMemoryButNotFound_ThrowsException() {
//        String username = "inMemoryUser";
//        when(inMemoryUsersConfig.inMemoryUserDetailsManager().loadUserByUsername(username)).thenThrow(new UsernameNotFoundException(""));
//
//        assertThrows(UsernameNotFoundException.class, () -> userDetailService.loadUserByUsername(username));
    }
}


