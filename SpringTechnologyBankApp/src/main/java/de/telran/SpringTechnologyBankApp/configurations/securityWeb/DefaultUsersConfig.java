package de.telran.SpringTechnologyBankApp.configurations.securityWeb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class DefaultUsersConfig {

    @Bean
    // 1212
    public UserDetailsService defaultUsers() {
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
        return new InMemoryUserDetailsManager(defaultAdmin, defaultManager, defaultClient);
    }
}
