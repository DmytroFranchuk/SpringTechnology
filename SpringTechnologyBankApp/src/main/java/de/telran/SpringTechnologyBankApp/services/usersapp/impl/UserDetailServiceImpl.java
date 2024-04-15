package de.telran.SpringTechnologyBankApp.services.usersapp.impl;

import de.telran.SpringTechnologyBankApp.configurations.securityWeb.InMemoryUsersConfig;
import de.telran.SpringTechnologyBankApp.entities.usersapp.UserApplication;
import de.telran.SpringTechnologyBankApp.repositories.usersapp.UserApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserApplicationRepository userApplicationRepository;
    private final InMemoryUsersConfig inMemoryUsersConfig;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserDetails inMemoryUser = inMemoryUsersConfig.inMemoryUserDetailsManager().loadUserByUsername(username);
            if (inMemoryUser != null) {
                return inMemoryUser;
            }
        } catch (UsernameNotFoundException e) {
            //
        }
        UserApplication userApp = userApplicationRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Нет пользователя по имени: " + username));
        return new User(userApp.getUsername(), userApp.getPassword(), userApp.getRoles());
    }
}
