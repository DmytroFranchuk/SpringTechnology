package de.telran.SpringTechnologyBankApp.configurations.securityWeb;

import de.telran.SpringTechnologyBankApp.entities.usersapp.UserApplication;
import de.telran.SpringTechnologyBankApp.repositories.usersapp.UserApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@RequiredArgsConstructor
public class UsersConfig
//        implements UserDetailsService
{
//    private final UserApplicationRepository userApplicationRepository;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserApplication userApp = userApplicationRepository.findByLogin(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Нет пользователя по имени: " + username));
//        return User.builder()
//                .username(userApp.getLogin())
//                .password(userApp.getPassword())
//                .roles(userApp.getRoles().stream().map(role -> role.getRoleType().toString()).toArray(String[]::new))
//                .build();
//    }

    @Bean
    // 1212
    public UserDetailsService defaultUsers() {
        UserDetails superAppUser = User.builder()
                .username("superUserApp")
                .password("$2a$10$STSRftoUAwfWwAzWcRpc7.6VJNjwyka7N68Zq2YQlSopwyTG7/5h6")
                .roles("REGISTRAR")
                .build();
        UserDetails defaultAdmin = User.builder()
                .username("admin")
                .password("$2a$10$cs0f0w748.vyBxl8agJcre1DZ2jgkJmA8Cc7YD7FRjUiG9DxXX4Ka")
                .roles("ADMIN")
                .build();
        UserDetails defaultManager = User
                .builder()
                .username("manager")
                .password("$2a$10$zd8HWgtHUG6cBVokUYjse.qRkxx6qrJxhcWxRWWUr1Htr5SwgpiZ2")
                .roles("MANAGER")
                .build();
        UserDetails defaultClient = User.builder()
                .username("client")
                .password("$2a$10$XB/t3f/RlwZ4wE2Wek9hkuNkBTYRPnHLAqzjxuSn/K525nwULDUR6")
                .roles("CLIENT")
                .build();
        return new InMemoryUserDetailsManager(superAppUser, defaultAdmin, defaultManager, defaultClient);
    }
}
